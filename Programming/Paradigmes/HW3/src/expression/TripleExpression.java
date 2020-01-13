package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException;
}
