package expression;

public strictfp class Add extends Operator {
    public Add(AllExpression fir, AllExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir + sec;
    }

    protected double apply(double fir, double sec) {
        return fir + sec;
    }
}
