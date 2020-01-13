package expression;

public class Add extends Operator {
    public Add(Expression fir, Expression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir + sec;
    }
}
