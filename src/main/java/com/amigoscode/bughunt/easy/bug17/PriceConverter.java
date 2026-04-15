package com.amigoscode.bughunt.easy.bug17;

public class PriceConverter {

    private static final int CENTS_PER_DOLLAR = 100;
    private final String currencySymbol;

    public PriceConverter(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public int toCents(double dollars) {
        return (int) dollars * CENTS_PER_DOLLAR;
    }

    public double fromCents(int cents) {
        return cents / (double) CENTS_PER_DOLLAR;
    }

    public String format(double dollars) {
        return currencySymbol + String.format("%.2f", dollars);
    }

    public String describe(double dollars) {
        return format(dollars) + " (" + toCents(dollars) + " cents)";
    }
}
