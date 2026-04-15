package com.amigoscode.bughunt.easy.bug06;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class AverageTest {

    private final Average a = new Average();

    @Test
    void averageOfThreeNumbers() {
        assertThat(a.average(3, 6, 9)).isCloseTo(6.0, within(0.0001));
    }

    @Test
    void averageOfEqualNumbers() {
        assertThat(a.average(10, 10, 10)).isCloseTo(10.0, within(0.0001));
    }
}
