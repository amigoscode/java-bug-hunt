package com.amigoscode.bughunt.medium.bug88;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceCalculatorTest {

    private final DistanceCalculator calc = new DistanceCalculator();

    @Test
    void distanceShouldBePositiveForSimpleValues() {
        assertThat(calc.distance(10, 3)).isEqualTo(7L);
    }

    @Test
    void distanceShouldBePositiveForOverflowCase() {
        // BUG: Integer.MAX_VALUE - (-1) overflows; Math.abs returns negative
        long result = calc.distance(Integer.MAX_VALUE, -1);

        assertThat(result).isGreaterThan(0L);
    }

    @Test
    void distanceShouldHandleMinValueToPositive() {
        // BUG: Integer.MIN_VALUE - 0 overflows
        long result = calc.distance(Integer.MIN_VALUE, 0);

        assertThat(result).isGreaterThan(0L);
    }

    @Test
    void totalPathDistanceShouldSumConsecutiveDistances() {
        long total = calc.totalPathDistance(Arrays.asList(0, 10, 3, 7));

        // |10-0| + |3-10| + |7-3| = 10 + 7 + 4 = 21
        assertThat(total).isEqualTo(21L);
    }

    @Test
    void farthestPairShouldFindExtremes() {
        int[] values = {5, 1, 9, 3};
        int[] pair = calc.farthestPair(values);

        // 1 and 9 are farthest apart (indices 1 and 2)
        assertThat(pair).containsExactly(1, 2);
    }

    @Test
    void minConsecutiveDistanceShouldReturnSmallest() {
        long min = calc.minConsecutiveDistance(Arrays.asList(10, 12, 20, 21));

        // |12-10|=2, |20-12|=8, |21-20|=1 → min is 1
        assertThat(min).isEqualTo(1L);
    }
}
