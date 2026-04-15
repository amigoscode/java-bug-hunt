package com.amigoscode.bughunt.easy.bug04;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class PercentageTest {

    private final Percentage p = new Percentage();

    @Test
    void computesFiftyPercent() {
        assertThat(p.percentage(1, 2)).isCloseTo(50.0, within(0.0001));
    }

    @Test
    void computesQuarter() {
        assertThat(p.percentage(3, 12)).isCloseTo(25.0, within(0.0001));
    }
}
