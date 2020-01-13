package expression;

public strictfp class Subtract extends Operator {
    public Subtract(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir - sec;
    }
}
