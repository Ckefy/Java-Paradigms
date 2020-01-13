package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperator {
    public CheckedNegate(TripleExpression fir) {
        super(fir);
    }

    protected void check (int fir) throws OverflowException {
        if (fir == Integer.MIN_VALUE){
            throw new OverflowException();
        }
    }

    protected int apply(int fir) throws OverflowException {
        check(fir);
        return -fir;
    }
}
