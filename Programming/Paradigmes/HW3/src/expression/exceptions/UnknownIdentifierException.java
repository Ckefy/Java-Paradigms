package expression.exceptions;


public class UnknownIdentifierException extends ParsingException {
    public UnknownIdentifierException (String identifier) {
        super ("Unknown Identifier " + identifier);
    }
}
