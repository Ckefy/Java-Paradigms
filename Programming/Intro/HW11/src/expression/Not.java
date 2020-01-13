package expression;

public class Not extends UnaryOperator {
    public Not(TripleExpression x) {
        super(x);
    }

    protected int apply(int x) {
        return -x;
    }
}
