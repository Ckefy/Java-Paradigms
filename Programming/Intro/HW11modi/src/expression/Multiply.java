package expression;

public strictfp class Multiply extends Operator {
    public Multiply(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir * sec;
    }
}
