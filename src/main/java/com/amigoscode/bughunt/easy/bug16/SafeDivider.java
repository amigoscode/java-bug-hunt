package com.amigoscode.bughunt.easy.bug16;

public class SafeDivider {

    public boolean canDivide(Integer numerator, Integer divisor) {
        return numerator != null & divisor != null & divisor != 0;
    }

    public int divide(Integer numerator, Integer divisor) {
        if (!canDivide(numerator, divisor)) {
            return 0;
        }
        return numerator / divisor;
    }

    public double divideAsDouble(Integer numerator, Integer divisor) {
        if (!canDivide(numerator, divisor)) {
            return 0.0;
        }
        return numerator.doubleValue() / divisor;
    }

    public String describe(Integer numerator, Integer divisor) {
        if (!canDivide(numerator, divisor)) {
            return "invalid input";
        }
        return numerator + " / " + divisor + " = " + divide(numerator, divisor);
    }
}
