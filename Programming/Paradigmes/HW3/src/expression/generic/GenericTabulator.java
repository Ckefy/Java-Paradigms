package expression.generic;

import expression.TripleExpression;
import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import modes.*;

public class GenericTabulator implements Tabulator {
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Mode<?> nowMode = getMode(mode);
        return makeArray(nowMode, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] makeArray(Mode<T> nowMode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> nowParser = new ExpressionParser<>(nowMode);
        TripleExpression<T> parsedExpression;
        try {
            parsedExpression = nowParser.parse(expression);
        } catch (OverflowException | IllegalOperationException | ParsingException e) {
            return ans;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        ans[i - x1][j - y1][k - z1] = parsedExpression.evaluate(nowMode.getNumber(i), nowMode.getNumber(j), nowMode.getNumber(k));
                    } catch (OverflowException | IllegalOperationException e) {
                        ans[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return ans;
    }

    private Mode<?> getMode(String mode) {
        switch (mode) {
            case "i":
                return new IntegerMode(true);
            case "u":
                return new IntegerMode(false);
            case "d":
                return new DoubleMode();
            case "bi":
                return new BigIntegerMode();
            case "f":
                return new FloatMode();
            case "b":
                return new ByteMode();
            case "s":
                return new ShortMode();
            case "l":
                return new LongMode();
            default:
                return null;
        }
    }
}
