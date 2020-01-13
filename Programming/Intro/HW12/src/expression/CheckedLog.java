package expression;

import expression.exceptions.IllegalOperationException;

public class CheckedLog extends UnaryOperator {
    public CheckedLog(TripleExpression fir) {
        super(fir);
    }

    protected void check(int fir) throws IllegalOperationException {
        if (fir <= 0) {
            throw new IllegalOperationException("Log form negative");
        }
    }

    protected int apply(int fir) throws IllegalOperationException {
        check(fir);
        int ans = 0;
        while (fir >= 2) {
            fir /= 2;
            ans++;
        }
        return ans;
    }
}