package modes;

public class DoubleMode implements Mode<Double> {
    public Double getNumber(int number) {
        return (double) number;
    }

    public Double add(Double first, Double second) {
        return first + second;
    }

    public Double sub(Double first, Double second) {
        return first - second;
    }

    public Double mul(Double first, Double second) {
        return first * second;
    }

    public Double div(Double first, Double second) {
        if (second == 0) {
            if (first == 0) {
                return Double.NaN;
            }
            return (first > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
        }
        return first / second;
    }

    public Double mod(Double first, Double second) {
        if (second == 0) {
            return Double.NaN;
        }
        return first % second;
    }

    public Double not(Double argument) {
        return -argument;
    }

    public Double abs(Double argument) {
        return Math.abs(argument);
    }
}
