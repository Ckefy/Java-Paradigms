package modes;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;

public class IntegerMode implements Mode<Integer> {
    private boolean needCheck;

    public IntegerMode(boolean check) {
        needCheck = check;
    }

    public Integer getNumber(int number) {
        return number;
    }

    public Integer add(Integer first, Integer second) throws OverflowException {
        if (needCheck) {
            checkAdd(first, second);
        }
        return first + second;
    }

    public Integer sub(Integer first, Integer second) throws OverflowException {
        if (needCheck) {
            checkSub(first, second);
        }
        return first - second;
    }

    public Integer mul(Integer first, Integer second) throws OverflowException {
        if (needCheck) {
            checkMul(first, second);
        }
        return first * second;
    }

    public Integer div(Integer first, Integer second) throws IllegalOperationException, OverflowException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        if (needCheck) {
            checkDiv(first, second);
        }
        return first / second;
    }

    public Integer mod(Integer first, Integer second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return first % second;
    }

    public Integer not(Integer argument) throws OverflowException {
        if (needCheck) {
            checkNot(argument);
        }
        return -argument;
    }

    public Integer abs(Integer argument) throws OverflowException {
        if (needCheck) {
            checkAbs(argument);
        }
        return Math.abs(argument);
    }

    private void checkAdd(Integer first, Integer second) throws OverflowException {
        if (first > 0 && Integer.MAX_VALUE - first < second) {
            throw new OverflowException();
        }
        if (first < 0 && Integer.MIN_VALUE - first > second) {
            throw new OverflowException();
        }
    }

    private void checkSub(Integer first, Integer second) throws OverflowException {
        if (first >= 0 && second < 0 && first - Integer.MAX_VALUE > second) {
            throw new OverflowException();
        }
        if (first <= 0 && second > 0 && Integer.MIN_VALUE - first > -second) {
            throw new OverflowException();
        }
    }

    private void checkMul(Integer first, Integer second) throws OverflowException {
        if (first > 0 && second > 0 && Integer.MAX_VALUE / first < second) {
            throw new OverflowException();
        }
        if (first > 0 && second < 0 && Integer.MIN_VALUE / first > second) {
            throw new OverflowException();
        }
        if (first < 0 && second > 0 && Integer.MIN_VALUE / second > first) {
            throw new OverflowException();
        }
        if (first < 0 && second < 0 && Integer.MAX_VALUE / first > second) {
            throw new OverflowException();
        }
    }

    private void checkDiv(Integer first, Integer second) throws OverflowException {
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new OverflowException();
        }
    }

    private void checkNot(Integer argument) throws OverflowException {
        if (argument == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    private void checkAbs(Integer argument) throws OverflowException {
        if (argument == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
}
