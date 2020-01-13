package expression;

import expression.exceptions.OverflowException;

public class High extends UnaryOperator {
    public High(TripleExpression first) {
        super(first);
    }

    protected void check (int first) throws OverflowException {
    }

    protected int apply(int first) throws OverflowException {
        first = Integer.highestOneBit(first);
        return first;
    }
}