package com.amigoscode.bughunt.easy.bug18;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AverageRaterTest {

    @Test
    void roundsUpWhenAverageAboveHalf() {
        AverageRater r = new AverageRater("book");
        r.addRating(5);
        r.addRating(4);
        r.addRating(4);
        // 13 / 3 = 4.333..., rounds to 4
        assertThat(r.averageRounded()).isEqualTo(4);
    }

    @Test
    void roundsUpAtHalf() {
        AverageRater r = new AverageRater("book");
        r.addRating(5);
        r.addRating(4);
        // 9 / 2 = 4.5, rounds to 5
        assertThat(r.averageRounded()).isEqualTo(5);
    }

    @Test
    void averageOfOne() {
        AverageRater r = new AverageRater("book");
        r.addRating(3);
        assertThat(r.averageRounded()).isEqualTo(3);
    }
}
