package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import modes.Mode;

public class Negate<T> extends UnaryOperator<T> {
    public Negate(TripleExpression<T> first, Mode<T> givenMode) {
        super(first, givenMode);
    }

    protected T apply(T first) throws OverflowException {
        return nowMode.not(first);
    }
}
