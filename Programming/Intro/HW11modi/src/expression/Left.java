package expression;

public class Left extends Operator {
    public Left(TripleExpression fir, TripleExpression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir << sec;
    }
}
