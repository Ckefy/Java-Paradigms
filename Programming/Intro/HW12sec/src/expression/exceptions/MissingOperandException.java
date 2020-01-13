package expression.exceptions;

import java.io.IOException;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(int index) {
        super("Missing operand at position:" + index);
    }
}
