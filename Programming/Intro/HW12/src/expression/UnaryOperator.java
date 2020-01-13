package expression;

import expression.exceptions.*;

public abstract class UnaryOperator implements TripleExpression {
    protected final TripleExpression fir;

    protected UnaryOperator(TripleExpression x) {
        fir = x;
    }

    protected abstract int apply(int x) throws EvaluatingException;

    protected abstract void check (int x) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(fir.evaluate(x, y, z));
    }

}
