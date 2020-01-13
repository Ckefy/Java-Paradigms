package expression;

public abstract class UnaryOperator implements TripleExpression {
    protected final TripleExpression fir;

    protected UnaryOperator(TripleExpression x) {
        fir = x;
    }

    protected abstract int apply(int x);

    public int evaluate(int x, int y, int z) {
        return apply(fir.evaluate(x, y, z));
    }

}
