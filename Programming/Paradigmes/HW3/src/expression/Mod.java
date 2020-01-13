package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import modes.Mode;

public class Mod<T> extends Operator<T> {
    public Mod(final TripleExpression<T> x, final TripleExpression<T> y, final Mode<T> givenMode) {
        super(x, y, givenMode);
    }

    protected T apply(final T x, final T y) throws OverflowException, IllegalOperationException {
        return nowMode.mod(x, y);
    }
}