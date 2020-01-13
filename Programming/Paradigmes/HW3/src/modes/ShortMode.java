package modes;

import expression.exceptions.IllegalOperationException;

public class ShortMode implements Mode<Short> {
    public Short getNumber(int number) {
        return (short) number;
    }

    public Short add(Short first, Short second) {
        return (short) (first + second);
    }

    public Short sub(Short first, Short second) {
        return (short) (first - second);
    }

    public Short mul(Short first, Short second) {
        return (short) (first * second);
    }

    public Short div(Short first, Short second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return (short) (first / second);
    }

    public Short mod(Short first, Short second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return (short) (first % second);
    }

    public Short not(Short argument) {
        return (short) -argument;
    }

    public Short abs(Short argument) {
        return (short) Math.abs(argument);
    }
}
