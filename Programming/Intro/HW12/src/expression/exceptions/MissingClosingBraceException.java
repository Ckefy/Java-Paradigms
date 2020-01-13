package expression.exceptions;

import java.io.IOException;

public class MissingClosingBraceException extends ParsingException {
    public MissingClosingBraceException () {
        super ("Missed closing brace for the opening one");
    }
}
