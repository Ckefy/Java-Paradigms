package expression;

public strictfp class Divide extends Operator {
    public Divide(AllExpression fir, AllExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir / sec;
    }

    protected double apply(double fir, double sec) {
        return fir / sec;
    }
}
