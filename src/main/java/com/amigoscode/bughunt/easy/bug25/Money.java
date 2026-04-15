package com.amigoscode.bughunt.easy.bug25;

import java.util.Objects;

public class Money {

    private final long cents;
    private final String currency;

    public Money(long cents, String currency) {
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency required");
        }
        this.cents = cents;
        this.currency = currency;
    }

    public long cents() {
        return cents;
    }

    public String currency() {
        return currency;
    }

    public Money plus(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(cents + other.cents, currency);
    }

    public boolean equals(Money other) {
        if (other == null) {
            return false;
        }
        return this.cents == other.cents
                && Objects.equals(this.currency, other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cents, currency);
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", currency, cents / 100.0);
    }
}
