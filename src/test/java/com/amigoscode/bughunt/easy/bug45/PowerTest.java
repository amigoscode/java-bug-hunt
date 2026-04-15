package com.amigoscode.bughunt.easy.bug45;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PowerTest {

    private final Power p = new Power();

    @Test
    void twoToTheZero() {
        assertThat(p.power(2, 0)).isEqualTo(1L);
    }

    @Test
    void twoToTheOne() {
        assertThat(p.power(2, 1)).isEqualTo(2L);
    }

    @Test
    void twoToTheTwo() {
        assertThat(p.power(2, 2)).isEqualTo(4L);
    }

    @Test
    void twoToTheThree() {
        assertThat(p.power(2, 3)).isEqualTo(8L);
    }

    @Test
    void twoToTheTen() {
        assertThat(p.power(2, 10)).isEqualTo(1024L);
    }

    @Test
    void threeToTheFive() {
        assertThat(p.power(3, 5)).isEqualTo(243L);
    }
}
