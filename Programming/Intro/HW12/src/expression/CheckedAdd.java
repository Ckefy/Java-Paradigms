package expression;

import expression.exceptions.*;

public class CheckedAdd extends Operator {
    public CheckedAdd(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected void check(int fir, int sec) throws OverflowException {
        if ((fir > 0 && Integer.MAX_VALUE - fir < sec) || (fir < 0 && Integer.MIN_VALUE - fir > sec)) {
            throw new OverflowException();
        }

    }

    protected int apply(int fir, int sec) throws OverflowException {
        check(fir, sec);
        return fir + sec;
    }
}
