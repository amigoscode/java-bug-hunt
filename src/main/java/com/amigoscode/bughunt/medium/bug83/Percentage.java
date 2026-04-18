package com.amigoscode.bughunt.medium.bug83;

import java.util.Objects;

/**
 * A record representing a percentage value in the range [0, 100].
 * Both 0% and 100% are valid values.
 *
 * <p>BUG: The compact constructor validation uses {@code >= 100} instead of
 * {@code > 100}, which incorrectly rejects exactly 100%. A percentage of
 * 100% is a perfectly valid value (e.g. "100% completion").
 */
public record Percentage(double value) {

    /**
     * Compact constructor that validates the percentage is within the
     * allowed range.
     *
     * <p>BUG: the upper-bound check is {@code value >= 100} -- this rejects
     * 100 itself, which should be valid.
     */
    public Percentage {
        // BUG: >= 100 should be > 100
        if (value < 0 || value >= 100) {
            throw new IllegalArgumentException(
                    "Percentage must be between 0 and 100 inclusive, got: " + value);
        }
    }

    /**
     * Returns this percentage as a fraction (0.0 to 1.0).
     *
     * @return the fractional representation
     */
    public double asFraction() {
        return value / 100.0;
    }

    /**
     * Returns the complement of this percentage (i.e. 100 - value).
     *
     * @return a new Percentage representing the complement
     */
    public Percentage complement() {
        return new Percentage(100 - value);
    }

    /**
     * Scales the given amount by this percentage.
     * For example, 25% of 200 returns 50.
     *
     * @param amount the amount to scale
     * @return the scaled result
     */
    public double of(double amount) {
        return amount * asFraction();
    }

    /**
     * Adds two percentages, capping at 100%.
     *
     * @param other the other percentage to add
     * @return a new Percentage whose value is min(this + other, 100)
     */
    public Percentage add(Percentage other) {
        Objects.requireNonNull(other, "other must not be null");
        double sum = Math.min(value + other.value, 100);
        return new Percentage(sum);
    }

    /**
     * Returns a display string like "42.5%".
     */
    public String display() {
        if (value == Math.floor(value)) {
            return (int) value + "%";
        }
        return String.format("%.1f%%", value);
    }

    /**
     * Returns true if this percentage represents a majority (strictly
     * greater than 50%).
     *
     * @return true if value > 50
     */
    public boolean isMajority() {
        return value > 50;
    }

    /**
     * Subtracts the other percentage from this one, flooring at 0%.
     *
     * @param other the percentage to subtract
     * @return a new Percentage whose value is max(this - other, 0)
     */
    public Percentage subtract(Percentage other) {
        Objects.requireNonNull(other, "other must not be null");
        double diff = Math.max(value - other.value, 0);
        return new Percentage(diff);
    }

    @Override
    public String toString() {
        return "Percentage[value=" + value + "]";
    }
}
