package com.amigoscode.bughunt.medium.bug90;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SensorValidatorTest {

    private final SensorValidator validator = new SensorValidator();

    @Test
    void isValidShouldRejectNaN() {
        // BUG: reading != Double.NaN is always true, so NaN passes as valid
        assertThat(validator.isValid(Double.NaN)).isFalse();
    }

    @Test
    void isValidShouldAcceptNormalReading() {
        assertThat(validator.isValid(42.0)).isTrue();
    }

    @Test
    void isValidShouldRejectNegative() {
        assertThat(validator.isValid(-5.0)).isFalse();
    }

    @Test
    void isValidShouldRejectAboveMax() {
        assertThat(validator.isValid(1500.0)).isFalse();
    }

    @Test
    void filterValidShouldExcludeNaN() {
        List<Double> readings = Arrays.asList(10.0, Double.NaN, 20.0, -1.0, 30.0);

        // BUG: NaN is not filtered out because isValid(NaN) returns true
        List<Double> valid = validator.filterValid(readings);

        assertThat(valid).containsExactly(10.0, 20.0, 30.0);
    }

    @Test
    void countInvalidShouldIncludeNaN() {
        List<Double> readings = Arrays.asList(10.0, Double.NaN, -1.0);

        // BUG: NaN is counted as valid, so countInvalid reports 1 instead of 2
        assertThat(validator.countInvalid(readings)).isEqualTo(2);
    }

    @Test
    void averageValidShouldExcludeNaN() {
        List<Double> readings = Arrays.asList(10.0, Double.NaN, 20.0);

        // BUG: NaN leaks into the average, making it NaN
        double avg = validator.averageValid(readings);

        assertThat(avg).isEqualTo(15.0);
    }
}
