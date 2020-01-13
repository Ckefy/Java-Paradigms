package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperator {
    public CheckedNegate(TripleExpression first) {
        super(first);
    }

    protected void check (int first) throws OverflowException {
        if (first == Integer.MIN_VALUE){
            throw new OverflowException();
        }
    }

    protected int apply(int first) throws OverflowException {
        check(first);
        return -first;
    }
}
