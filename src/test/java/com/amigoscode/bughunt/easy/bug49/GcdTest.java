package com.amigoscode.bughunt.easy.bug49;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GcdTest {

    private final Gcd g = new Gcd();

    @Test
    void gcdOfTwelveAndEight() {
        assertThat(g.gcd(12, 8)).isEqualTo(4);
    }

    @Test
    void gcdOfFifteenAndSix() {
        assertThat(g.gcd(15, 6)).isEqualTo(3);
    }

    @Test
    void gcdOfCoprimes() {
        assertThat(g.gcd(7, 9)).isEqualTo(1);
    }

    @Test
    void gcdOfAnythingAndZero() {
        assertThat(g.gcd(42, 0)).isEqualTo(42);
    }

    @Test
    void gcdOfEqualValues() {
        assertThat(g.gcd(6, 6)).isEqualTo(6);
    }
}
