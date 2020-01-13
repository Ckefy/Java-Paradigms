package expression;

import expression.exceptions.*;
import modes.Mode;

public class Add<T> extends Operator<T> {
    public Add(TripleExpression<T> first, TripleExpression<T> second, Mode<T> givenMode) {
        super(first, second, givenMode);
    }

    protected T apply(T first, T second) throws OverflowException {
        return nowMode.add(first, second);
    }
}
