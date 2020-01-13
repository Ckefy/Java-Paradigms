package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import modes.Mode;

public class Square<T> extends UnaryOperator<T> {
    public Square(final TripleExpression<T> x, final Mode<T> givenMode) {
        super(x, givenMode);
    }

    protected T apply(final T x) throws OverflowException {
        return nowMode.sqr(x);
    }
}