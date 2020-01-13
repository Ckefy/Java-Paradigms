package expression;

public class Const<T> implements TripleExpression<T> {

    private final T value;

    public Const(T arg) {
        value = arg;
    }

    public T evaluate(T x, T y, T z) {
        return value;
    }
}