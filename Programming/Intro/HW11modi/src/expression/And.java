package expression;

public class And extends Operator {
    public And(final TripleExpression fir, final TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(final int x, final int y) {
        return x & y;
    }

}
