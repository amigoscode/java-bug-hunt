package com.amigoscode.bughunt.easy.bug24;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartTest {

    @Test
    void removesAllMatchingItems() {
        ShoppingCart cart = new ShoppingCart("ada");
        cart.add("red apple");
        cart.add("green apple");
        cart.add("banana");
        cart.add("apple pie");

        cart.removeAllContaining("apple");

        assertThat(cart.items()).containsExactly("banana");
    }

    @Test
    void removesNothingWhenNoMatch() {
        ShoppingCart cart = new ShoppingCart("ada");
        cart.add("banana");
        cart.add("cherry");

        cart.removeAllContaining("apple");

        assertThat(cart.items()).containsExactly("banana", "cherry");
    }
}
