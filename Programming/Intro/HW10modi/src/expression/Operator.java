package expression;

public strictfp abstract class Operator implements AllExpression {
    private final AllExpression first;
    private final AllExpression second;

    public Operator(AllExpression fir, AllExpression sec) {
        first = fir;
        second = sec;
    }

    protected abstract int apply(int x, int y);

    protected abstract double apply(double x, double y);

    public int evaluate(int x) {
        return apply(first.evaluate(x), second.evaluate(x));
    }

    public double evaluate(double x) {
        return apply(first.evaluate(x), second.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
