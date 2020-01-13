package expression;

import expression.exceptions.*;

public abstract class UnaryOperator implements TripleExpression {
    protected final TripleExpression first;

    protected UnaryOperator(TripleExpression x) {
        first = x;
    }

    protected abstract int apply(int x) throws EvaluatingException;

    protected abstract void check (int x) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(first.evaluate(x, y, z));
    }

}
