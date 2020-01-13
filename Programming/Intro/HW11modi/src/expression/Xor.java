package expression;

public class Xor extends Operator{
    public Xor(final TripleExpression fir, final TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(final int x, final int y) {
        return x ^ y;
    }
}
