package com.amigoscode.bughunt.easy.bug17;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceConverterTest {

    private final PriceConverter converter = new PriceConverter("$");

    @Test
    void convertsWholeDollarsToCents() {
        assertThat(converter.toCents(5.0)).isEqualTo(500);
    }

    @Test
    void convertsFractionalDollarsToCents() {
        assertThat(converter.toCents(1.99)).isEqualTo(199);
    }

    @Test
    void convertsPennyToCents() {
        assertThat(converter.toCents(0.01)).isEqualTo(1);
    }
}
