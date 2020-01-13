package expression;

import expression.exceptions.*;
import modes.Mode;

public class Divide<T> extends Operator<T> {
    public Divide(TripleExpression<T> first, TripleExpression<T> second, Mode<T> givenMode) {
        super(first, second, givenMode);
    }

    protected T apply(T first, T second) throws OverflowException, IllegalOperationException {
        return nowMode.div(first,second);
    }
}
