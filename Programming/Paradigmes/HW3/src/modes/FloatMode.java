package modes;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;

public class FloatMode implements Mode<Float> {
    public Float getNumber(int number) {
        return (float) number;
    }

    public Float add(Float first, Float second) {
        return first + second;
    }

    public Float sub(Float first, Float second) {
        return first - second;
    }

    public Float mul(Float first, Float second) {
        return first * second;
    }

    public Float div(Float first, Float second) {
        if (second == 0) {
            if (first == 0) {
                return Float.NaN;
            }
            return (first > 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY);
        }
        return first / second;
    }

    public Float mod(Float first, Float second) {
        if (second == 0) {
            return Float.NaN;
        }
        return first % second;
    }

    public Float not(Float argument) {
        return -argument;
    }

    public Float abs(Float argument) {
        return Math.abs(argument);
    }
}
