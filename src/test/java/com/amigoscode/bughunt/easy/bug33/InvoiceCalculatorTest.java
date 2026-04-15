package com.amigoscode.bughunt.easy.bug33;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCalculatorTest {

    @Test
    void subtotalOfWholeNumberPrices() {
        InvoiceCalculator inv = new InvoiceCalculator(0);
        inv.addLine("A", 2, 5.0);
        inv.addLine("B", 1, 3.0);

        assertThat(inv.subtotal()).isEqualByComparingTo("13.00");
    }

    @Test
    void subtotalOfFractionalPrice() {
        InvoiceCalculator inv = new InvoiceCalculator(0);
        inv.addLine("A", 1, 0.10);
        inv.addLine("B", 1, 0.20);

        assertThat(inv.subtotal()).isEqualByComparingTo(new BigDecimal("0.30"));
    }
}
