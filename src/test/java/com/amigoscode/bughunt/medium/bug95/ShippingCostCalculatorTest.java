package com.amigoscode.bughunt.medium.bug95;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingCostCalculatorTest {

    @Test
    void costForAsiaShouldReturnAValue() {
        ShippingCostCalculator calc = new ShippingCostCalculator("NYC");

        // ASIA is a valid region, should return a reasonable shipping cost
        double asiaCost = calc.cost("ASIA");

        assertThat(asiaCost).isGreaterThan(0.0);
    }

    @Test
    void costForAfricaShouldReturnAValue() {
        ShippingCostCalculator calc = new ShippingCostCalculator("LON");

        // AFRICA is a valid region, should return a shipping cost
        double africaCost = calc.cost("AFRICA");

        assertThat(africaCost).isGreaterThan(0.0);
    }

    @Test
    void knownRegionsShouldReturnCorrectCosts() {
        ShippingCostCalculator calc = new ShippingCostCalculator("NYC");

        assertThat(calc.cost("US")).isEqualTo(5.99);
        assertThat(calc.cost("EU")).isEqualTo(8.99);
        assertThat(calc.cost("UK")).isEqualTo(7.99);
        assertThat(calc.cost("CA")).isEqualTo(6.49);
    }

    @Test
    void formattedCostShouldIncludeDollarSign() {
        ShippingCostCalculator calc = new ShippingCostCalculator("LAX");

        String formatted = calc.formattedCost("US");

        assertThat(formatted).isEqualTo("$5.99");
    }

    @Test
    void totalCalculationsShouldIncrement() {
        ShippingCostCalculator calc = new ShippingCostCalculator("SFO");

        calc.cost("US");
        calc.cost("EU");
        calc.cost("UK");

        assertThat(calc.totalCalculations()).isEqualTo(3);
    }
}
