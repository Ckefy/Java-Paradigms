package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import modes.Mode;

public class Subtract<T> extends Operator<T> {
    public Subtract(TripleExpression<T> first, TripleExpression<T> second, Mode<T> givenMode) {
        super(first, second, givenMode);
    }

    protected T apply(T first, T second) throws OverflowException {
        //check(first, second);
        return nowMode.sub(first, second);
    }
}
