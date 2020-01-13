package expression.exceptions;

import java.io.IOException;

public class MissingOpeningBraceException extends ParsingException {
    public MissingOpeningBraceException (int index) {
        super ("Missed opening brace for the opening one at position: " + index);
    }
}
