package expression;

public class Inv extends UnaryOperator {
    public Inv(TripleExpression x) {
        super(x);
    }

    protected int apply(int x) {
        return ~x;
    }
}
