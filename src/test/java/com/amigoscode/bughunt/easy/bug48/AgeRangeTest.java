package com.amigoscode.bughunt.easy.bug48;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AgeRangeTest {

    private final AgeRange adult = new AgeRange("adult", 18, 65);

    @Test
    void midrangeAgeIsInside() {
        assertThat(adult.contains(30)).isTrue();
    }

    @Test
    void minBoundaryIsInside() {
        assertThat(adult.contains(18)).isTrue();
    }

    @Test
    void maxBoundaryIsInside() {
        assertThat(adult.contains(65)).isTrue();
    }

    @Test
    void belowMinIsOutside() {
        assertThat(adult.contains(17)).isFalse();
    }

    @Test
    void aboveMaxIsOutside() {
        assertThat(adult.contains(66)).isFalse();
    }
}
