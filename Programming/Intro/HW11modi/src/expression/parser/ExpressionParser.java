package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    private int index = 0;
    private String expression;
    private int value;
    private char name;

    private enum Token {VARIABLE, CONST, NOT, ADD, SUB, DIV, MUL, OPEN_BRACE, CLOSE_BRACE, END, NOTHING, XOR, OR, AND, COUNT, INV, LEFT, RIGHT}

    private Token now = Token.NOTHING;

    private void spaces() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void getToken() {
        spaces();
        if (index >= expression.length()) {
            now = Token.END;
            return;
        }
        switch (expression.charAt(index)) {
            case '^':
                now = Token.XOR;
                break;
            case '&':
                now = Token.AND;
                break;
            case '|':
                now = Token.OR;
                break;
            case '+':
                now = Token.ADD;
                break;
            case '-':
                if (now == Token.CONST || now == Token.VARIABLE || now == Token.CLOSE_BRACE) {
                    now = Token.SUB;
                } else {
                    now = Token.NOT;
                }
                break;
            case '*':
                now = Token.MUL;
                break;
            case '/':
                now = Token.DIV;
                break;
            case '(':
                now = Token.OPEN_BRACE;
                break;
            case ')':
                now = Token.CLOSE_BRACE;
                break;
            case '~':
                now = Token.INV;
                break;
            case '>':
                now = Token.RIGHT;
                index++;
                break;
            case '<':
                now = Token.LEFT;
                index++;
                break;
            case 'c':
                index += 4;
                now = Token.COUNT;
                break;
            default:
                char c = expression.charAt(index);
                if (Character.isDigit(c)) {
                    int start = index;
                    while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                        index++;
                    }
                    int end = index;
                    value = Integer.parseUnsignedInt(expression.substring(start, end));
                    now = Token.CONST;
                    index--;
                } else if (c == 'x' || c == 'y' || c == 'z') {
                    name = c;
                    now = Token.VARIABLE;
                }
        }
        index++;
    }

    private TripleExpression unary() {
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
                ans = new Not(unary());
                break;
            case OPEN_BRACE:
                ans = leftright();
                getToken();
                break;
            case INV:
                ans = new Inv(unary());
                break;
            case COUNT:
                ans = new Count(unary());
                break;
            default:
                return new Const(0);
        }
        return ans;
    }

    private TripleExpression highPriority() {
        TripleExpression ans = unary();
        do {
            switch (now) {
                case MUL:
                    ans = new Multiply(ans, unary());
                    break;
                case DIV:
                    ans = new Divide(ans, unary());
                    break;
                default:
                    return ans;
            }
        } while (true);
    }

    private TripleExpression lowPriority() {
        TripleExpression ans = highPriority();
        do {
            switch (now) {
                case ADD:
                    ans = new Add(ans, highPriority());
                    break;
                case SUB:
                    ans = new Subtract(ans, highPriority());
                    break;
                default:
                    return ans;
            }
        } while (true);
    }

    private TripleExpression and() {
        TripleExpression res = lowPriority();
        do {
            switch (now) {
                case AND:
                    res = new And(res, lowPriority());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression xor() {
        TripleExpression res = and();
        do {
            switch (now) {
                case XOR:
                    res = new Xor(res, and());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression or() {
        TripleExpression res = xor();
        do {
            switch (now) {
                case OR:
                    res = new Or(res, xor());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression leftright() {
        TripleExpression res = or();
        do {
            switch (now) {
                case LEFT:
                    res = new Left(res, or());
                    break;
                case RIGHT:
                    res = new Right(res, or());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    public TripleExpression parse(String s) {
        index = 0;
        expression = s;
        return leftright();
    }
}
