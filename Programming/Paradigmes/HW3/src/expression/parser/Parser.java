package expression.parser;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;
import expression.TripleExpression;

public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws OverflowException, IllegalOperationException, ParsingException;
}
