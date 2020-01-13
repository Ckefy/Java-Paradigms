package expression;

public class Count extends UnaryOperator {
    public Count(TripleExpression x) {
        super(x);
    }

    protected int apply(int x) {
        return Integer.bitCount(x);
    }
}
