package com.amigoscode.bughunt.easy.bug07;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReverserTest {

    private final Reverser r = new Reverser();

    @Test
    void reversesWord() {
        assertThat(r.reverse("java")).isEqualTo("avaj");
    }

    @Test
    void reversesEmpty() {
        assertThat(r.reverse("")).isEmpty();
    }
}
