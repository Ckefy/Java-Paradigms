package expression;

public strictfp class Const implements AllExpression {

    private final Number value;

    public Const(Number x) {
        value = x;
    }

    public int evaluate(int x) {
        return value.intValue();
    }

    public double evaluate(double x) {
        return value.doubleValue();
    }

    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }
}
