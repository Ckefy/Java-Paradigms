package expression.exceptions;

import java.io.IOException;

public class UnknownIdentifierException extends ParsingException {
    public UnknownIdentifierException () {
        super ("Unknown Identifier");
    }
}
