package com.amigoscode.bughunt.easy.bug21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryTest {

    @Test
    void sameStockForSmallValues() {
        Inventory inv = new Inventory("main");
        inv.set("widget", 10);
        inv.set("gadget", 10);
        assertThat(inv.sameStockLevel("widget", "gadget")).isTrue();
    }

    @Test
    void sameStockForLargeValues() {
        Inventory inv = new Inventory("main");
        inv.set("widget", 5000);
        inv.set("gadget", 5000);
        assertThat(inv.sameStockLevel("widget", "gadget")).isTrue();
    }

    @Test
    void differentStockIsFalse() {
        Inventory inv = new Inventory("main");
        inv.set("widget", 10);
        inv.set("gadget", 20);
        assertThat(inv.sameStockLevel("widget", "gadget")).isFalse();
    }
}
