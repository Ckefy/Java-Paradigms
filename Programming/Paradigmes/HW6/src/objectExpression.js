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

Operator.prototype.toString = function () {
    let results = '';
    for (const i of this.operands) {
        results += i.toString() + ' ';
    }
    return results + this.type;
};

Operator.prototype.prefix = function () {
    let results = '(' + this.type + ' ';
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

    let newElem = Object.create(Operator.prototype);
    newElem.constructor = Constructor;
    newElem.type = type;
    newElem.count = count;
    newElem.countDiff = countDiff;
    Constructor.prototype = newElem;
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

Const.prototype.toString = Const.prototype.prefix = function () {
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

Variable.prototype.toString = Variable.prototype.prefix = function () {
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

//вот это все надо модно переписать tl;dr

/*function ArcTan(operand) {
    Operator.call(this, operand);
    this.type = 'atan'
}

ArcTan.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
ArcTan.prototype.constructor = ArcTan;
ArcTan.prototype.count = function (operand) {
    return Math.atan(operand);
};

ArcTan.prototype.countDiff = function (firstOperand, firstOperand_diff) {
    return new Divide(firstOperand_diff, new Add(ONE, new Multiply(firstOperand, firstOperand)));
};

function ArcTan2(firstOperand, secondOperand) {
    Operator.call(this, firstOperand, secondOperand);
    this.type = 'atan2';
}

ArcTan2.prototype.countDiff = function (firstOperand, firstOperand_diff, secondOperand, secondOperand_diff) {
    return new Divide(firstOperand_diff, new Add(ONE, new Multiply(firstOperand, firstOperand)));
};

ArcTan2.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
ArcTan2.prototype.constructor = ArcTan2;
ArcTan2.prototype.count = function (firstOperand, secondOperand) {
    return Math.atan2(firstOperand, secondOperand);
};

ArcTan2.prototype.countDiff = function (firstOperand, firstOperand_diff, secondOperand, secondOperand_diff) {
    return new Multiply(new Divide(ONE, new Add(ONE, new Multiply(new Divide(firstOperand, secondOperand), new Divide(firstOperand, secondOperand)))),
        new Divide(new Subtract(new Multiply(firstOperand_diff, secondOperand), new Multiply(firstOperand, secondOperand_diff)), new Multiply(secondOperand, secondOperand)));
};

function Min3(firstOperand, secondOperand, thirdOperand) {
    Operator.call(this, firstOperand, secondOperand, thirdOperand);
    this.type = 'min3';
}

Min3.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
Min3.prototype.constructor = Min3;
Min3.prototype.count = function (firstOperand, secondOperand, thirdOperand) {
    return Math.min(firstOperand, secondOperand, thirdOperand);
};

function Max5(firstOperand, secondOperand, thirdOperand, fourthOperand, fifthOperand) {
    Operator.call(this, firstOperand, secondOperand, thirdOperand, fourthOperand, fifthOperand);
    this.type = 'max5';
}

function Sinh(operand) {
    Operator.call(this, operand);
    this.type = 'sinh'
}

Sinh.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
Sinh.prototype.constructor = Sinh;
Sinh.prototype.count = function (operand) {
    return Math.sinh(operand);
};

Sinh.prototype.countDiff = function (firstOperand, firstOperand_diff) {
    return new Multiply(new Cosh(firstOperand), firstOperand_diff);
};

function Cosh(operand) {
    Operator.call(this, operand);
    this.type = 'cosh'
}

Cosh.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
Cosh.prototype.constructor = Cosh;
Cosh.prototype.count = function (operand) {
    return Math.cosh(operand);
};

Cosh.prototype.countDiff = function (firstOperand, firstOperand_diff) {
    return new Multiply(new Sinh(firstOperand), firstOperand_diff);
};

Max5.prototype = Object.create(Operator.prototype); //Теперь add -> add.proto -> oper.proto
Max5.prototype.constructor = Max5;
Max5.prototype.count = function (firstOperand, secondOperand, thirdOperand, fourthOperand, fifthOperand) {
    return Math.max(firstOperand, secondOperand, thirdOperand, fourthOperand, fifthOperand);
};*/

const variables = ["x", "y", "z"];
const operationsMap = {
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1]
    //раскомментите меня, когда переделают наверху функции
    /*"atan": [ArcTan, 1],
    "atan2": [ArcTan2, 2],
    "min3": [Min3, 3],
    "max5": [Max5, 5],
    "sinh": [Sinh, 1],
    "cosh": [Cosh, 1]*/
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

    if (isNumber(expression, index)) {
        index++;
        while (index < expression.length && isDigit(expression[index])) {
            index++;
        }
        return [expression.substring(beginIndex, index), index];
    } else if (expression[index] === '(' || expression[index] === ')')
        return [expression[index], index + 1];
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
    while (index !== expression.length && expression.charAt(index) === ' ') {
        index++;
    }
    return index;
}

function parse(expression, modeFoo) {
    if (expression.trim() === "") {
        throw new ParsingException("Empty expression", null, -1);
    }
    expression = expression.trim();
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
            let countOfArguments = operationsMap[token][1];
            if (args.length < countOfArguments) {
                throw new ParsingException("Need more arguments for operation " + token, expression, parsed[2]);
            } else if (args.length > countOfArguments) {
                throw new ParsingException("Need less arguments for operation " + token, expression, parsed[2]);
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

