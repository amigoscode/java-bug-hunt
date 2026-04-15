package com.amigoscode.bughunt.easy.bug02;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SummerTest {

    private final Summer summer = new Summer();

    @Test
    void sumsAllElements() {
        assertThat(summer.sum(new int[]{1, 2, 3, 4})).isEqualTo(10);
    }

    @Test
    void sumOfEmptyIsZero() {
        assertThat(summer.sum(new int[]{})).isZero();
    }
}
