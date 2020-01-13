package expression;

public strictfp class Variable implements TripleExpression {
   private final char var;

    public Variable(char x) {
        var = x;
    }

    public int evaluate(int x, int y, int z) {
        switch (var) {
            case 'x':
                return x;
            case 'y':
                return y;
            default:
                return z;
        }
    }

}
