package expression.parser;

import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;
import expression.TripleExpression;

public interface Parser {
    TripleExpression parse(String expression) throws ParsingException, OverflowException;
}
