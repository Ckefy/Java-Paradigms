import expression.TripleExpression;
import expression.exceptions.EvaluatingException;
import expression.exceptions.ParsingException;
import expression.parser.ExpressionParser;

public class Main {

    public static void main(String[] args) throws ParsingException, EvaluatingException {
	    String s = "-2147483649";
        ExpressionParser a = new ExpressionParser();
	    int ans = a.parse(s).evaluate(10,0,0);
        System.out.println(ans);
    }
}
