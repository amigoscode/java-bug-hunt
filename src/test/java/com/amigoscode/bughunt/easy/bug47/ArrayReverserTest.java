package com.amigoscode.bughunt.easy.bug47;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayReverserTest {

    private final ArrayReverser r = new ArrayReverser();

    @Test
    void reversesEvenLengthArray() {
        assertThat(r.reversed(new int[]{1, 2, 3, 4}))
                .containsExactly(4, 3, 2, 1);
    }

    @Test
    void reversesOddLengthArray() {
        assertThat(r.reversed(new int[]{1, 2, 3, 4, 5}))
                .containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    void reversesSingletonArray() {
        assertThat(r.reversed(new int[]{42})).containsExactly(42);
    }

    @Test
    void detectsPalindrome() {
        assertThat(r.isPalindrome(new int[]{1, 2, 3, 2, 1})).isTrue();
    }
}
