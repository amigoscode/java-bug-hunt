package com.amigoscode.bughunt.medium.bug83;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class PercentageTest {

    @Test
    void shouldAcceptZeroPercent() {
        Percentage pct = new Percentage(0);

        assertThat(pct.value()).isEqualTo(0);
    }

    @Test
    void shouldAcceptOneHundredPercent() {
        // BUG: this throws because the validation uses >= 100
        Percentage pct = new Percentage(100);

        assertThat(pct.value()).isEqualTo(100);
    }

    @Test
    void shouldRejectNegative() {
        assertThatThrownBy(() -> new Percentage(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectAboveHundred() {
        assertThatThrownBy(() -> new Percentage(100.1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void asFractionShouldReturnDecimal() {
        Percentage pct = new Percentage(50);

        assertThat(pct.asFraction()).isCloseTo(0.5, within(0.0001));
    }

    @Test
    void complementOfTwentyShouldBeEighty() {
        Percentage pct = new Percentage(20);

        // BUG: complement creates new Percentage(80), which works,
        // but complement of 0 creates new Percentage(100) -- which fails!
        Percentage comp = new Percentage(0).complement();

        assertThat(comp.value()).isEqualTo(100);
    }

    @Test
    void ofShouldScaleAmount() {
        Percentage pct = new Percentage(25);

        double result = pct.of(200);

        assertThat(result).isCloseTo(50.0, within(0.001));
    }

    @Test
    void displayShouldFormatNicely() {
        assertThat(new Percentage(42).display()).isEqualTo("42%");
        assertThat(new Percentage(33.3).display()).isEqualTo("33.3%");
    }
}
