package expression.exceptions;

import java.io.IOException;

public class MissingOperandException extends ParsingException {
    public MissingOperandException() {
        super("Missing operand");
    }
}
