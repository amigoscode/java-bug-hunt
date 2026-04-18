package com.amigoscode.bughunt.medium.bug90;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Validates sensor readings, filtering out invalid values such as NaN,
 * negative numbers, and readings outside an acceptable range.
 *
 * <p>BUG: {@link #isValid(double)} uses {@code reading != Double.NaN} to
 * detect NaN. However, by IEEE 754 rules, {@code NaN != NaN} evaluates to
 * {@code true}, so NaN readings are never rejected. The fix is to use
 * {@link Double#isNaN(double)}.
 */
public class SensorValidator {

    private final double maxReading;

    /**
     * Creates a validator with a default maximum reading of 1000.0.
     */
    public SensorValidator() {
        this(1000.0);
    }

    /**
     * Creates a validator with the specified maximum acceptable reading.
     *
     * @param maxReading the upper bound for valid readings
     */
    public SensorValidator(double maxReading) {
        this.maxReading = maxReading;
    }

    /**
     * Checks whether a single reading is valid.
     *
     * <p>BUG: {@code reading != Double.NaN} is always true because NaN is
     * not equal to anything, including itself.
     *
     * @param reading the sensor reading
     * @return true if the reading is valid
     */
    public boolean isValid(double reading) {
        // BUG: reading != Double.NaN is always true -- NaN is never filtered
        // NaN also passes !(reading < 0) because NaN < 0 is false
        // and !(reading > maxReading) because NaN > maxReading is false
        return reading != Double.NaN
                && !(reading < 0)
                && !(reading > maxReading);
    }

    /**
     * Filters a list of readings, returning only the valid ones.
     *
     * @param readings the raw readings
     * @return a list containing only valid readings
     */
    public List<Double> filterValid(List<Double> readings) {
        Objects.requireNonNull(readings, "readings must not be null");
        List<Double> valid = new ArrayList<>();
        for (double r : readings) {
            if (isValid(r)) {
                valid.add(r);
            }
        }
        return valid;
    }

    /**
     * Counts how many readings in the list are invalid.
     *
     * @param readings the raw readings
     * @return the count of invalid readings
     */
    public int countInvalid(List<Double> readings) {
        Objects.requireNonNull(readings, "readings must not be null");
        int count = 0;
        for (double r : readings) {
            if (!isValid(r)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the average of all valid readings in the list.
     *
     * @param readings the raw readings
     * @return the average, or {@code 0.0} if no valid readings exist
     */
    public double averageValid(List<Double> readings) {
        Objects.requireNonNull(readings, "readings must not be null");
        List<Double> valid = filterValid(readings);
        if (valid.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (double v : valid) {
            sum += v;
        }
        return sum / valid.size();
    }

    /**
     * Returns the configured maximum reading.
     *
     * @return the max reading
     */
    public double maxReading() {
        return maxReading;
    }

    @Override
    public String toString() {
        return "SensorValidator{maxReading=" + maxReading + "}";
    }
}
