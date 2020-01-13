package expression;

public class CheckedMax extends Operator {
    public CheckedMax (TripleExpression fir, TripleExpression sec){
        super (fir, sec);
    }

    protected void check (int fir, int sec){
    }

    protected int apply (int fir, int sec){
        return fir > sec ? fir : sec;
    }
}
