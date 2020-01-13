package expression;

public class Subtract extends Operator {
    public Subtract(Expression fir, Expression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir - sec;
    }
}
