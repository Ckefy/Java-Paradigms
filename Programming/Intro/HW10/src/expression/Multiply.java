package expression;

public class Multiply extends Operator {
    public Multiply(Expression fir, Expression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir * sec;
    }
}
