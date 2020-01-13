package expression;

import expression.exceptions.*;

public strictfp abstract class Operator implements TripleExpression {
    private final TripleExpression first;
    private final TripleExpression second;

    public Operator(TripleExpression fir, TripleExpression sec) {
        first = fir;
        second = sec;
    }

    protected abstract int apply(int x, int y) throws EvaluatingException;

    protected abstract void check(int x, int y) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
