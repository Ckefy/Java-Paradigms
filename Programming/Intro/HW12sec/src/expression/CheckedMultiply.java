package expression;

import expression.exceptions.*;

public class CheckedMultiply extends Operator {
    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected void check(int first, int second) throws OverflowException {
        if ((first > 0 && second > 0 && Integer.MAX_VALUE / first < second) || (first < 0 && second < 0 && Integer.MAX_VALUE / first > second)) {
            throw new OverflowException();
        }
        if ((first > 0 && second < 0 && Integer.MIN_VALUE / first > second) || (first < 0 && second > 0 && Integer.MIN_VALUE / second > first)) {
            throw new OverflowException();
        }
    }

    protected int apply(int first, int second) throws OverflowException {
        check(first, second);
        return first * second;
    }
}
