package expression;

import expression.exceptions.IllegalOperationException;

public class CheckedPow extends UnaryOperator {
    public CheckedPow(TripleExpression fir) {
        super(fir);
    }

    protected void check(int fir) throws IllegalOperationException {
        if (fir > 31 || fir < 0) {
            throw new IllegalOperationException("Log form negative");
        }
    }

    protected int apply(int fir) throws IllegalOperationException {
        check (fir);
        return 1<<fir;
    }
}