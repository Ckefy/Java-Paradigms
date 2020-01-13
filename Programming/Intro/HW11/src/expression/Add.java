package expression;

public strictfp class Add extends Operator {
    public Add(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir + sec;
    }
}
