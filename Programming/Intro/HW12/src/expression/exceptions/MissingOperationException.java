package expression.exceptions;

import java.io.IOException;

public class MissingOperationException extends ParsingException {
    public MissingOperationException() {
        super("Missing operation");
    }
}
