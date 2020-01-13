package expression;

public class Divide extends Operator {
    public Divide(Expression fir, Expression sec) {
        super(fir, sec);
    }

    protected int apply(int fir, int sec) {
        return fir / sec;
    }
}
