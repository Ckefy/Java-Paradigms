package expression.exceptions;

import java.io.IOException;

public class MissingOpeningBraceException extends ParsingException {
    public MissingOpeningBraceException () {
        super ("Missed opening brace for the opening one");
    }
}
