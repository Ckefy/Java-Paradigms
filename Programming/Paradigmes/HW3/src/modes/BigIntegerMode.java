package modes;

import expression.exceptions.IllegalOperationException;

import java.math.BigInteger;

public class BigIntegerMode implements Mode<BigInteger> {
    public BigInteger getNumber(int number) {//IncorrectConstException
        return BigInteger.valueOf(number);
    }

    public BigInteger add(BigInteger first, BigInteger second) {
        return first.add(second);
    }

    public BigInteger sub(BigInteger first, BigInteger second) {
        return first.subtract(second);
    }

    public BigInteger mul(BigInteger first, BigInteger second) {
        return first.multiply(second);
    }

    public BigInteger div(BigInteger first, BigInteger second) throws IllegalOperationException {
        if (second.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException("Div by zero");
        }
        return first.divide(second);
    }

    public BigInteger mod(BigInteger first, BigInteger second) throws IllegalOperationException {
        if (second.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return first.mod(second);
    }

    public BigInteger not(BigInteger argument) {
        return argument.negate();
    }

    public BigInteger abs(BigInteger argument) {
        return argument.abs();
    }
}
