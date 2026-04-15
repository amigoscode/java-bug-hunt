package com.amigoscode.bughunt.easy.bug11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class CoffeeOrderTest {

    @Test
    void smallPricedCorrectly() {
        assertThat(new CoffeeOrder("S", 2, false).total())
                .isCloseTo(5.00, within(0.0001));
    }

    @Test
    void largePricedCorrectly() {
        assertThat(new CoffeeOrder("L", 1, false).total())
                .isCloseTo(3.50, within(0.0001));
    }

    @Test
    void extraLargePricedCorrectly() {
        assertThat(new CoffeeOrder("XL", 1, false).total())
                .isCloseTo(4.50, within(0.0001));
    }

    @Test
    void extraShotAddsCost() {
        assertThat(new CoffeeOrder("M", 1, true).total())
                .isCloseTo(3.75, within(0.0001));
    }
}
