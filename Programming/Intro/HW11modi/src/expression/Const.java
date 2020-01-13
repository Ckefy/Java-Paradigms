package expression;

public strictfp class Const implements TripleExpression {

    private final int value;

    public Const(int x) {
        value = x;
    }

    public int evaluate(int x, int y, int z) {
        return value;
    }
}
