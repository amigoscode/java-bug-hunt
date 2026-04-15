package com.amigoscode.bughunt.easy.bug08;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LastElementTest {

    private final LastElement le = new LastElement();

    @Test
    void returnsLastElement() {
        assertThat(le.last(new int[]{10, 20, 30})).isEqualTo(30);
    }

    @Test
    void returnsOnlyElement() {
        assertThat(le.last(new int[]{42})).isEqualTo(42);
    }
}
