package expression;

import expression.exceptions.OverflowException;

public class Low extends UnaryOperator {
    public Low(TripleExpression first) {
        super(first);
    }

    protected void check (int first) throws OverflowException {
    }

    protected int apply(int first) throws OverflowException {
        first = Integer.lowestOneBit(first);
        return first;
    }
}
