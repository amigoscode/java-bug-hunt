package com.amigoscode.bughunt.medium.bug51;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void copyCreatesIndependentOrder() {
        Order original = new Order("O1", "ada");
        original.addItem("widget", 2, 5.00);

        Order copy = original.copy("O2");
        copy.addItem("gadget", 1, 10.00);

        assertThat(original.lineCount()).isEqualTo(1);
        assertThat(copy.lineCount()).isEqualTo(2);
    }

    @Test
    void addingToOriginalDoesNotAffectCopy() {
        Order original = new Order("O1", "ada");
        original.addItem("widget", 2, 5.00);
        Order copy = original.copy("O2");

        original.addItem("extra", 1, 3.00);

        assertThat(copy.lineCount()).isEqualTo(1);
    }

    @Test
    void copyPreservesExistingItems() {
        Order original = new Order("O1", "ada");
        original.addItem("widget", 2, 5.00);
        Order copy = original.copy("O2");

        assertThat(copy.grandTotal()).isEqualTo(10.00);
    }
}
