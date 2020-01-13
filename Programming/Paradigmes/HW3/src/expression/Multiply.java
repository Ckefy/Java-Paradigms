package expression;

import expression.exceptions.*;
import modes.Mode;

public class Multiply<T> extends Operator<T> {
    public Multiply(TripleExpression<T> first, TripleExpression<T> second, Mode<T> givenMode) {
        super(first, second, givenMode);
    }

    protected T apply(T first, T second) throws OverflowException {
        //check(first, second);
        return nowMode.mul(first, second);
    }
}
