package expression;

import expression.exceptions.OverflowException;

public class CheckedSubtract extends Operator {
    public CheckedSubtract(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected void check(int fir, int sec) throws OverflowException {
        if ((fir >= 0 && sec < 0 && fir - Integer.MAX_VALUE > sec) || (fir <= 0 && sec > 0 && Integer.MIN_VALUE - fir > -sec)) {
            throw new OverflowException();
        }
    }

    protected int apply(int fir, int sec) throws OverflowException {
        check(fir, sec);
        return fir - sec;
    }
}
