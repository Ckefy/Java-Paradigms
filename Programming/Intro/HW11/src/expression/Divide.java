package expression;

public strictfp class Divide extends Operator {
    public Divide(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir / sec;
    }
}
