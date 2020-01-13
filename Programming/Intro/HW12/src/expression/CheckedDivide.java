package expression;

import expression.exceptions.*;

public class CheckedDivide extends Operator {
    public CheckedDivide(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected void check(int fir, int sec) throws IllegalOperationException, OverflowException {
        if (fir == Integer.MIN_VALUE && sec == -1) {
            throw new OverflowException();
        }
        if (sec == 0) {
            throw new IllegalOperationException("Division by zero");
        }
    }

    protected int apply(int fir, int sec) throws IllegalOperationException, OverflowException {
        check(fir, sec);
        return fir / sec;
    }
}
