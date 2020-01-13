import expression.TripleExpression;
import expression.exceptions.EvaluatingException;
import expression.exceptions.ParsingException;
import modes.*;
import expression.parser.ExpressionParser;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws Exception {
	/*    String s = "high -4";
        ExpressionParser a = new ExpressionParser();
	    int ans = a.parse(s).evaluate(10,0,0);
        System.out.println(ans);*/
	    /*String s = "10";
        ExpressionParser<Byte> a = new ExpressionParser<Byte>(new ByteMode());
        Byte ans = a.parse (s).evaluate((byte)2147483629, (byte) 2147483629,(byte) 2147483629);
        System.out.println(ans);*/
        System.out.println(-11 % 2);
    }
}
