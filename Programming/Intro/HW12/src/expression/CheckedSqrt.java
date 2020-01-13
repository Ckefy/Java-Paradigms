package expression;

import expression.exceptions.IllegalOperationException;

public class CheckedSqrt extends UnaryOperator {
    public CheckedSqrt(TripleExpression fir) {
        super(fir);
    }

    protected void check(int fir) throws IllegalOperationException {
        if (fir < 0) {
            throw new IllegalOperationException("Root from negative number");
        }
    }

    protected int apply(int fir) throws IllegalOperationException {
        check(fir);
        int left = 0;
        int right = 46340;
        while (right - left > 1) {
            int mid = left + (right - left) / 2;
            if (mid * mid <= fir) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
