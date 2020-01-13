package expression.exceptions;

import java.io.IOException;

public class MissingOperationException extends ParsingException {
    public MissingOperationException(int index) {
        super("Missing operation at position: " + index);
    }
}
