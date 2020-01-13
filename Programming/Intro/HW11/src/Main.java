import expression.TripleExpression;
import parser.ExpressionParser;
import parser.Parser;

public class Main {

    public static void main(String[] args) {
	    //String s = "x*(x+1)*x+ 1";
        String s = "x * (x - 2)*x + 1";
        ExpressionParser a = new ExpressionParser();
	    int ans = a.parse(s).evaluate(5,5,5);
        System.out.println(ans);
    }
}
