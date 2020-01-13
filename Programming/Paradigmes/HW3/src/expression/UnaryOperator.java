package expression;

import expression.exceptions.*;
import modes.Mode;

public abstract class UnaryOperator<T> implements TripleExpression<T> {
    protected final TripleExpression<T> argument;
    protected Mode<T> nowMode;

    protected UnaryOperator(TripleExpression<T> x, Mode<T> givenMode) {
        argument = x;
        nowMode = givenMode;
    }

    protected abstract T apply(T x) throws OverflowException, IllegalOperationException;

    public T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException{
        return apply(argument.evaluate(x, y, z));
    }

}
