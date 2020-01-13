package expression;

public class Or extends Operator {
    public Or(final TripleExpression fir, final TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(final int x, final int y) {
        return x | y;
    }
}
