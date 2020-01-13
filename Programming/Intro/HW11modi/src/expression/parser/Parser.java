package expression.parser;

import expression.CommonExpression;
import expression.TripleExpression;

public interface Parser {
    TripleExpression parse(String expression);
}
