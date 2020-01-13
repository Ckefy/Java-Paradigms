package expression;

import expression.exceptions.*;

public abstract class Operator implements TripleExpression {
    private final TripleExpression first;
    private final TripleExpression second;

    public Operator(TripleExpression x, TripleExpression y) {
        first = x;
        second = y;
    }

    protected abstract int apply(int x, int y) throws EvaluatingException;

    protected abstract void check(int x, int y) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
