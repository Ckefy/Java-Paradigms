package expression;

public strictfp class Variable implements AllExpression {
    private final String var;

    public Variable(String x) {
        var = x;
    }

    public int evaluate(int x) {
        return x;
    }

    public double evaluate(double x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x":
                return x;
            case "y":
                return y;
            default:
                return z;
        }
    }

}
