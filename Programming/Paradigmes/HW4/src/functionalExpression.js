"use strict";

let universal = function (operation) {
    return function (...operands) {
        //let operands = Array.from(arguments);
        return function (...value) { //evaluating
            //let value = arguments; //значение x, y, z
            let evaluatedOperands = operands.map(function (nowFunc) {
                return nowFunc.apply(null, value);
            }); //по всем operands[i] бежим и эвалуэйтим
            return operation.apply(null, evaluatedOperands);
        }
    }
};

let cnst = function (operand) {
    return function () {
        return operand;
    }
};

let variable = function (name) {
    return function () {
        return arguments[variablesMap[name]];
    }
};

let add = universal(function (firstOperand, secondOperand) {
    return firstOperand + secondOperand;
});

let subtract = universal(function (firstOperand, secondOperand) {
    return firstOperand - secondOperand;
});

let divide = universal(function (firstOperand, secondOperand) {
    return firstOperand / secondOperand;
});

let multiply = universal(function (firstOperand, secondOperand) {
    return firstOperand * secondOperand;
});

let avg5 = universal(function (firstOperand, secondOperand, thirdOperand, fourthOperand, fifthOperand) {
    return (firstOperand + secondOperand + thirdOperand + fourthOperand + fifthOperand) / 5;
});

let med3 = universal(function (firstOperand, secondOperand, thirdOperand) {
    let operands = [firstOperand, secondOperand, thirdOperand];
    operands.sort(function compareNumbers(a, b) {
        return a - b;
    });
    return operands[1];
});

let negate = universal(function (operand) {
    return -operand;
});

let abs = universal(function (operand) {
    return Math.abs(operand);
});

let iff = universal(function (firstOperand, secondOperand, thirdOperand) {
    return firstOperand >= 0 ? secondOperand : thirdOperand;
});

///hard mode on
let operMap = {
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide,
    "avg5": avg5,
    "med3": med3,
    "negate": negate,
    "abs": abs,
    "iff": iff
};

let numberArgMap = {
    "+": 2,
    "-": 2,
    "*": 2,
    "/": 2,
    "avg5": 5,
    "med3": 3,
    "negate": 1,
    "abs": 1,
    "iff": 3
};

let variablesMap = {
    "x": 0,
    "y": 1,
    "z": 2
};

let parse = function (expression) {
    let parsed = expression.split(" ");
    parsed = parsed.filter(function (element) {
        return element.length > 0;
    });
    let stack = [];
    //в стэк пихаем функции, операция их берет
    parsed.map(function(now){
        if (now in variablesMap) {
            stack.push(variable(now));
        } else if (now in operMap) {
            let countOfArgs = numberArgMap[now];
            let operands = stack.splice(stack.length - countOfArgs, countOfArgs);
            stack.push(operMap[now].apply(null, operands));
        } else {
            stack.push(cnst(+now));
        }
    });
    return stack.pop();
};

