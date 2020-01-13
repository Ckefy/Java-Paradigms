package expression;

public abstract class Operator implements Expression {
    private final Expression first;
    private final Expression second;

    public Operator (Expression fir, Expression sec){
        first = fir;
        second = sec;
    }

    protected abstract int apply (int x, int y);

    public int evaluate (int x){
        return apply(first.evaluate(x), second.evaluate(x));
    }
}
