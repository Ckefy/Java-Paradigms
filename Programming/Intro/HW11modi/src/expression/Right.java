package expression;

public class Right extends Operator {
    public Right(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir >> sec;
    }
}
