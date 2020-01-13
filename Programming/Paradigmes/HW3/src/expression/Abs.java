package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import modes.Mode;

public class Abs<T> extends UnaryOperator<T> {
    public Abs(final TripleExpression<T> x, final Mode<T> givenMode) {
        super(x, givenMode);
    }

    protected T apply(final T x) throws OverflowException {
        return nowMode.abs(x);
    }
}