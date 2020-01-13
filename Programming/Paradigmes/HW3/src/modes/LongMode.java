package modes;

import expression.exceptions.IllegalOperationException;

public class LongMode implements Mode<Long> {
    public Long getNumber(int number) {//IncorrectConstException
        return (long) number;
    }

    public Long add(Long first, Long second) {
        return first + second;
    }

    public Long sub(Long first, Long second) {
        return first - second;
    }

    public Long mul(Long first, Long second) {
        return first * second;
    }

    public Long div(Long first, Long second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return first / second;
    }

    public Long mod(Long first, Long second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return first % second;
    }

    public Long not(Long argument) {
        return -argument;
    }

    public Long abs(Long argument) {
        return Math.abs(argument);
    }
}
