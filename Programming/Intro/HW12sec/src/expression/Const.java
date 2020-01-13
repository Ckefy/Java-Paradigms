package expression;

public class Const implements TripleExpression {

    private final int value;

    public Const(int arg) {
        value = arg;
    }

    public int evaluate(int x, int y, int z) {
        return value;
    }
}