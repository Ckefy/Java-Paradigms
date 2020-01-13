package expression;

import expression.exceptions.*;

public class CheckedAdd extends Operator {
    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected void check(int first, int second) throws OverflowException {
        if ((first > 0 && Integer.MAX_VALUE - first < second) || (first < 0 && Integer.MIN_VALUE - first > second)) {
            throw new OverflowException();
        }

    }

    protected int apply(int first, int second) throws OverflowException {
        check(first, second);
        return first + second;
    }
}
