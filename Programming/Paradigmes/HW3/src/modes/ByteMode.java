package modes;

import expression.exceptions.IllegalOperationException;

import java.math.BigInteger;

public class ByteMode implements Mode<Byte> {
    public Byte getNumber(int number) {
        return (byte) number;
    }

    public Byte add(Byte first, Byte second) {
        return (byte) (first + second);
    }

    public Byte sub(Byte first, Byte second) {
        return (byte) (first - second);
    }

    public Byte mul(Byte first, Byte second) {
        return (byte) (first * second);
    }

    public Byte div(Byte first, Byte second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return (byte) (first / second);
    }

    public Byte mod(Byte first, Byte second) throws IllegalOperationException {
        if (second == 0) {
            throw new IllegalOperationException("Div by zero");
        }
        return (byte) (first % second);
    }

    public Byte not(Byte argument) {
        return (byte) (-argument);
    }

    public Byte abs(Byte argument) {
        return (byte) (Math.abs(argument));
    }
}
