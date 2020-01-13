package expression;

public class Variable implements Expression {
    private final String var;

    public Variable (String x){
        var = x;
    }

    public int evaluate (int x){
        return x;
    }

}
