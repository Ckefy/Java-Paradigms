"use strict";

function Operator(...args) {
    this.operands = args;
}

Operator.prototype.evaluate = function (...args) {
    let results = [];
    for (const i of this.operands) {
        results.push(i.evaluate.apply(i, args));
    }
    return this.count.apply(null, results);
};

Operator.prototype.toString = Operator.prototype.postfix = function () {
    let results = '(';
    if (this.operands.length === 0) {
        return results + ' ' + this.type + ')';
    }
    for (const i of this.operands) {
        results += i.postfix() + ' ';
    }
    return results + this.type + ')';
};

Operator.prototype.prefix = function () {
    let results = '(' + this.type + ' ';
    if (this.operands.length === 0) {
        return results + ')';
    }
    for (const i of this.operands) {
        results += i.prefix() + ' ';
    }
    return results.substring(0, results.length - 1) + ')';
};

Operator.prototype.diff = function (name) {
    let results = [];
    for (const i of this.operands) {
        results.push(i);
        results.push(i.diff(name));
    }
    return this.countDiff.apply(null, results);
};

function Universal(type, count, countDiff) {
    function Constructor(...args) {
        Operator.apply(this, args)
    }
    Constructor.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
    Constructor.constructor = Constructor;
    Constructor.prototype.type = type;
    Constructor.prototype.count = count;
    Constructor.prototype.countDiff = countDiff;
    return Constructor;
}

function Const(value) {
    this.value = value;
}

const ZERO = new Const(0);
const ONE = new Const(1);

Const.prototype.evaluate = function () {
    return this.value;
};

Const.prototype.toString = Const.prototype.prefix = Const.prototype.postfix = function () {
    return this.value.toString();
};

Const.prototype.diff = function () {
    return ZERO;
};

function Variable(name) {
    this.index = variables.indexOf(name);
}

Variable.prototype.evaluate = function (...args) {
    return args[this.index];
};

Variable.prototype.toString = Variable.prototype.prefix = Variable.prototype.postfix = function () {
    return variables[this.index];
};

Variable.prototype.diff = function (name) {
    return variables[this.index] === name ? ONE : ZERO;
};

let Add = Universal('+', function (firstOperand, secondOperand) {
    return firstOperand + secondOperand;
}, function (firstOperand, firstOperand_diff, secondOperand, secondOperand_diff) {
    return new Add(firstOperand_diff, secondOperand_diff);
});

let Subtract = Universal('-', function (firstOperand, secondOperand) {
    return firstOperand - secondOperand;
}, function (firstOperand, firstOperand_diff, secondOperand, secondOperand_diff) {
    return new Subtract(firstOperand_diff, secondOperand_diff);
});

let Multiply = Universal('*', function (firstOperand, secondOperand) {
    return firstOperand * secondOperand;
}, function (firstOperand, firstOperand_diff, secondOperand, secondOperand_diff) {
    return new Add(new Multiply(firstOperand_diff, secondOperand), new Multiply(secondOperand_diff, firstOperand));
});

let Divide = Universal('/', function (firstOperand, secondOperand) {
    return firstOperand / secondOperand;
}, function (firstOperand, firstOperand_diff, secondOperand, secondOperand_diff) {
    return new Divide(new Subtract(new Multiply(firstOperand_diff, secondOperand), new Multiply(secondOperand_diff, firstOperand)), new Multiply(secondOperand, secondOperand));
});

let Negate = Universal('negate', function (operand) {
    return -operand;
}, function (firstOperand, firstOperand_diff) {
    return new Negate(firstOperand_diff);
});

let Sum = Universal('sum', function (...args) {
    return args.reduce(function (result, nowElem) {
        return result + nowElem;
    }, 0)
}, null);

let Avg = Universal('avg', function (...args) {
    let nowSum = args.reduce(function (result, nowElem) {
        return result + nowElem;
    }, 0);
    return nowSum / args.length;
}, null);

let Sumexp = Universal('sumexp', function (...args) {
    return args.reduce(function (result, nowElem) {
        return result + Math.pow(Math.E, nowElem);
    }, 0)
}, null);

let Softmax = Universal('softmax', function (...args) {
    if (args.length === 0) {
        return;
    }
    let firstArgument = args[0];
    return Math.pow(Math.E, firstArgument) / args.reduce(function (result, nowElem) {
        return result + Math.pow(Math.E, nowElem);
    }, 0);
}, null);

let Sumsq = Universal('sumsq', function (...args) {
    return args.reduce(function (result, nowElem) {
        return result + Math.pow(nowElem, 2);
    }, 0)
}, null);

let Length = Universal('length', function (...args) {
    return Math.pow(args.reduce(function (result, nowElem) {
        return result + Math.pow(nowElem, 2);
    }, 0), 0.5)
}, null);

const variables = ["x", "y", "z"];
const operationsMap = {
    "sumexp": [Sumexp, null],
    "sumsq": [Sumsq, null],
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1],
    "sum": [Sum, null],
    "avg": [Avg, null],
    "softmax": [Softmax, null],
    "length": [Length, null]
};

function ParsingException(message, expression, errorIndex) {
    if (expression === null) {
        this.message = message;
    } else {
        this.message = message + " in index '" + errorIndex + "'" + "\n";
        this.message += "'" + expression + "'" + "\n";
        this.message += " ".repeat(errorIndex + 1) + "^" + "\n";
    }
}

ParsingException.prototype = Object.create(Error.prototype);
ParsingException.prototype.name = "ParsingException";
ParsingException.prototype.constructor = ParsingException;

function isDigit(nowChar) {
    return nowChar >= '0' && nowChar <= '9';
}

function isNumber(expression, index) {
    return (isDigit(expression.charAt(index))) || (expression.charAt(index) === '-' && index + 1 < expression.length && isDigit(expression.charAt(index + 1)));
}

function isLetter(nowChar) {
    return nowChar >= 'a' && nowChar <= 'z';
}

function isThisOperation(expression, operation, index) {
    if (expression.length - index < operation.length)
        return false;
    return expression.substring(index, index + operation.length) === operation;
}

//будем ретурнать указатель ЗА токен
function nextToken(expression, index) {
    let beginIndex = index;
    //braces
    if (expression.charAt(index) === '(' || expression.charAt(index) === ')') {
        return [expression.charAt(index), index + 1];
    }
    //numbers
    if (isNumber(expression, index)) {
        index++;
        while (index < expression.length && isDigit(expression.charAt(index))) {
            index++;
        }
        return [expression.substring(beginIndex, index), index];
    }
    //operations
    for (const i in operationsMap) {
        if (isThisOperation(expression, i, index)) {
            index += i.length;
            return [i, index];
        }
    }
    //variable
    while (isLetter(expression.charAt(index))) {
        index++;
    }
    let probablyName = expression.substring(beginIndex, index);
    if (variables.indexOf(probablyName) !== -1) {
        return [probablyName, index];
    }
    //some exception
    while (index < expression.length && expression.charAt(index) !== ' ') {
        index++;
    }
    throw new ParsingException("Undefined token '" + expression.substring(beginIndex, index) + "'", expression, beginIndex);
}

function skipWhiteSpaces(expression, index) {
    while (index < expression.length && expression.charAt(index) === ' ') {
        index++;
    }
    return index;
}

function parse(expression, modeFoo) {
    expression = expression.trim();
    if (expression === "") {
        throw new ParsingException("Empty expression", null, -1);
    }
    let stack = [];
    let index = 0;
    let balance = 0;
    while (index < expression.length) {
        index = skipWhiteSpaces(expression, index);
        let beginIndex = index;
        let tokenPair = nextToken(expression, index);
        let token = tokenPair[0];
        index = tokenPair[1];
        if (token === ')') {
            //some exceptions
            if (stack[stack.length - 1][0] === '(') {
                throw new ParsingException("Empty braces without expression", expression, stack[stack.length - 1][1]);
            }
            if (--balance < 0) {
                throw new ParsingException("Need opening brace", expression, index);
            }
            let parsed = modeFoo(stack, beginIndex);
            token = parsed[0];
            let args = parsed[1];
            if (operationsMap[token][1] !== null) {
                let countOfArguments = operationsMap[token][1];
                if (args.length < countOfArguments) {
                    throw new ParsingException("Need more arguments for operation " + token, expression, parsed[2]);
                } else if (args.length > countOfArguments) {
                    throw new ParsingException("Need less arguments for operation " + token, expression, parsed[2]);
                }
            }
            let newElem = Object.create(operationsMap[token][0].prototype);
            operationsMap[token][0].apply(newElem, args);
            stack.push([newElem, beginIndex]);
        } else if (token === '(') {
            balance++;
            stack.push([token, beginIndex]);
        } else if (token in operationsMap) {
            stack.push([token, beginIndex]);
        } else if (variables.indexOf(token) !== -1) {
            stack.push([new Variable(token), beginIndex]);
        } else if (isNumber(token, 0)) {
            stack.push([new Const(parseInt(token)), beginIndex]);
        }
    }
    if (balance !== 0) {
        throw new ParsingException("Need brace", expression, index);
    }
    if (stack.length !== 1) {
        throw new ParsingException("Some arguments out of braces", null, 0);
    }
    return stack[0][0];
}

let parsePrefix = function (expression) {
    let modeFoo = function (stack) {
        let args = [];
        while (!(stack[stack.length - 1][0] in operationsMap || stack[stack.length - 1][0] === '(')) {
            args.unshift(stack.pop()[0]);
        }
        let nowOperation = stack.pop();
        if (!(nowOperation[0] in operationsMap)) {
            throw new ParsingException("Need operation after '('", expression, (stack.length !== 0 ? stack.pop()[1] : 0));
        }
        let nowBrace = stack.pop();
        if (!(nowBrace[0] === "(")) {
            throw new ParsingException("Need opening brace before operation '" + nowOperation[0], expression, nowBrace[1]);
        }
        return [nowOperation[0], args, nowOperation[1]];
    };
    return parse(expression, modeFoo);
};

let parsePostfix = function (expression) {
    let modeFoo = function (stack, index) {
        let nowOperation = stack.pop();
        if (!(nowOperation[0] in operationsMap)) {
            throw new ParsingException("Need operation before ')'", expression, index);
        }
        let args = [];
        let countOfArguments = operationsMap[nowOperation[0]][1];
        for (let i = 0; (i < countOfArguments || countOfArguments === null) && stack[stack.length - 1][0] !== '('; i++) {
            let nowOperand = stack.pop();
            if (!(nowOperand[0] in operationsMap)) {
                args.unshift(nowOperand[0]);
            } else {
                throw new ParsingException("Another operation in braces", expression, nowOperand[1]);
            }
        }
        let nowBrace = stack.pop();
        if (!(nowBrace[0] === "(")) {
            throw new ParsingException("Need opening brace", expression, nowBrace[1]);
        }
        return [nowOperation[0], args, nowOperation[1]];
    };
    return parse(expression, modeFoo);
};

