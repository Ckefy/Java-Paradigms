import expression.TripleExpression;
import expression.parser.ExpressionParser;

public class Main {

    public static void main(String[] args) {
	    //String s = "x*(x+1)*x+ 1";
        String s = "count 5";
        ExpressionParser a = new ExpressionParser();
	    int ans = a.parse(s).evaluate(5,5,5);
        System.out.println(ans);
    }
}
