package expression.exceptions;


public class MissingClosingBraceException extends ParsingException {
    public MissingClosingBraceException (int index) {
        super ("Missed closing brace for the opening one at position: " + index);
    }
}
