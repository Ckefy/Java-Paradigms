package expression;

import expression.exceptions.*;

public class CheckedDivide extends Operator {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected void check(int first, int second) throws IllegalOperationException, OverflowException {
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new OverflowException();
        }
        if (second == 0) {
            throw new IllegalOperationException("Division by zero");
        }
    }

    protected int apply(int first, int second) throws IllegalOperationException, OverflowException {
        check(first, second);
        return first / second;
    }
}
