package expression.parser;

import expression.exceptions.*;
import expression.*;

import java.util.EnumSet;
import java.util.Set;

public class ExpressionParser implements Parser {
    private int index = 0;
    private int balanceBraces = 0;
    private String expression;
    private int value;
    private String name;

    private enum Token {BEGIN, VARIABLE, CONST, NOT, ADD, SUB, DIV, MUL, OPEN_BRACE, CLOSE_BRACE, HIGH, LOW, END}

    private static Set<Token> operations = EnumSet.of(Token.ADD, Token.SUB, Token.DIV, Token.MUL);

    private Token now;

    public int getIndex () {
        return index;
    }

    private void spaces() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void checkOperand() throws MissingOperandException { //если идет скобка операция, операция операция, начало файла операция
        if (now == Token.OPEN_BRACE || now == Token.BEGIN || operations.contains(now)) {
            throw new MissingOperandException(getIndex());
        }
    }

    private void checkOperation() throws MissingOperationException { //если идет закрывающаяся скобка число/переменная, переменная число или наоборот
        if (now == Token.CLOSE_BRACE || now == Token.VARIABLE || now == Token.CONST) {
            throw new MissingOperationException(getIndex());
        }
    }

    private void getToken() throws ParsingException, OverflowException {
        spaces();
        if (index >= expression.length()) {
            checkOperand();
            now = Token.END;
            return;
        }
        switch (expression.charAt(index)) {
            case '+':
                checkOperand();
                now = Token.ADD;
                break;
            case '-':
                if (now == Token.CONST || now == Token.VARIABLE || now == Token.CLOSE_BRACE) {
                    now = Token.SUB;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingOperandException(getIndex());
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                        int start = index;
                        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                            index++;
                        }
                        int end = index;
                        try {
                            value = Integer.parseInt("-" + expression.substring(start, end));
                        } catch (NumberFormatException e) {
                            throw new OverflowException();
                        }
                        ///
                        checkOperation();
                        ///
                        now = Token.CONST;
                        index--;
                    } else {
                        now = Token.NOT;
                    }
                }
                break;
            case '*':
                checkOperand();
                now = Token.MUL;
                break;
            case '/':
                checkOperand();
                now = Token.DIV;
                break;
            case '(':
                checkOperation();
                now = Token.OPEN_BRACE;
                balanceBraces++;
                break;
            case ')':
                checkOperand();
                balanceBraces--;
                if (balanceBraces < 0) {
                    throw new MissingOpeningBraceException(getIndex());
                }
                now = Token.CLOSE_BRACE;
                break;
            default:
                char nowChar = expression.charAt(index);
                StringBuilder nowString = new StringBuilder();
                if (Character.isDigit(nowChar)) {
                    int start = index;
                    while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                        index++;
                    }
                    int end = index;
                    try {
                        value = Integer.parseInt(expression.substring(start, end));
                    } catch (NumberFormatException e) {
                        throw new OverflowException();
                    }
                    checkOperation();
                    now = Token.CONST;
                    index--;
                } else {
                    while (Character.isLetterOrDigit(expression.charAt(index))) {
                        nowString.append(expression.charAt(index));
                        index++;
                        if (index >= expression.length()) {
                            break;
                        }
                    }
                    index--;
                    switch (nowString.toString()) {
                        case "x":
                        case "y":
                        case "z":
                            checkOperation();
                            name = nowString.toString();
                            now = Token.VARIABLE;
                            break;
                        case "high":
                            ////
                            checkOperation();
                            now = Token.HIGH;
                            break;
                        case "low":
                            ////
                            checkOperation();
                            now = Token.LOW;
                            break;
                        default:
                            throw new UnknownIdentifierException(nowString.toString());
                    }
                }

        }
        index++;
    }

    private TripleExpression unary() throws ParsingException, OverflowException {
        getToken();
        TripleExpression ans;
        switch (now) {
            case CONST:
                ans = new Const(value);
                getToken();
                break;
            case VARIABLE:
                ans = new Variable(name);
                getToken();
                break;
            case NOT:
                ans = new CheckedNegate(unary());
                break;
            case OPEN_BRACE:
                ans = lowPriority();
                if (now != Token.CLOSE_BRACE) {
                    throw new MissingClosingBraceException(getIndex());
                }
                getToken();
                break;
            case HIGH:
                ans = new High(unary());
                break;
            case LOW:
                ans = new Low(unary());
                break;
            default:
                throw new ParsingException("Bug with expression: " + expression);
        }
        return ans;
    }

    private TripleExpression highPriority() throws ParsingException, OverflowException {
        TripleExpression ans = unary();
        do {
            switch (now) {
                case MUL:
                    ans = new CheckedMultiply(ans, unary());
                    break;
                case DIV:
                    ans = new CheckedDivide(ans, unary());
                    break;
                default:
                    return ans;
            }
        } while (true);
    }

    private TripleExpression lowPriority() throws ParsingException, OverflowException {
        TripleExpression ans = highPriority();
        do {
            switch (now) {
                case ADD:
                    ans = new CheckedAdd(ans, highPriority());
                    break;
                case SUB:
                    ans = new CheckedSubtract(ans, highPriority());
                    break;
                default:
                    return ans;
            }
        } while (true);
    }

    public TripleExpression parse(String s) throws ParsingException, OverflowException {
        index = 0;
        expression = s;
        now = Token.BEGIN;
        balanceBraces = 0;
        return lowPriority();
    }
}
