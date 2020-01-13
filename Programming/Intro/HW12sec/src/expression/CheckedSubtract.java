package expression;

import expression.exceptions.OverflowException;

public class CheckedSubtract extends Operator {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected void check(int first, int second) throws OverflowException {
        if ((first >= 0 && second < 0 && first - Integer.MAX_VALUE > second) || (first <= 0 && second > 0 && Integer.MIN_VALUE - first > -second)) {
            throw new OverflowException();
        }
    }

    protected int apply(int first, int second) throws OverflowException {
        check(first, second);
        return first - second;
    }
}
