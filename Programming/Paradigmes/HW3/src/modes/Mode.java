package modes;


import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;

public interface Mode<T> {
    T getNumber(int number);

    T add(T first, T second) throws OverflowException;

    T mul(T first, T second) throws OverflowException;

    T sub(T first, T second) throws OverflowException;

    T div(T first, T second) throws OverflowException, IllegalOperationException;

    T mod(T first, T second) throws IllegalOperationException;

    T not(T argument) throws OverflowException;

    default T sqr(T argument) throws OverflowException {
        return mul(argument, argument);
    }

    T abs(T argument) throws OverflowException;


}
