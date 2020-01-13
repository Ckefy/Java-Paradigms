package expression;

import expression.exceptions.*;
import modes.Mode;

public abstract class Operator<T> implements TripleExpression<T> {
    private final TripleExpression<T> first;
    private final TripleExpression<T> second;
    protected Mode<T> nowMode;

    public Operator(TripleExpression<T> x, TripleExpression<T> y, Mode<T> givenMode) {
        first = x;
        second = y;
        nowMode = givenMode;
    }

    protected abstract T apply(T x, T y) throws OverflowException, IllegalOperationException;

    public T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException{
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
