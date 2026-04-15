package com.amigoscode.bughunt.easy.bug42;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BinarySearchTest {

    private final BinarySearch search = new BinarySearch(new int[]{1, 3, 5, 7, 9, 11});

    @Test
    void findsMiddleElement() {
        assertThat(search.indexOf(5)).isEqualTo(2);
    }

    @Test
    void findsFirstElement() {
        assertThat(search.indexOf(1)).isEqualTo(0);
    }

    @Test
    void findsLastElement() {
        assertThat(search.indexOf(11)).isEqualTo(5);
    }

    @Test
    void missingElementReturnsMinusOne() {
        assertThat(search.indexOf(4)).isEqualTo(-1);
    }

    @Test
    void findsInSingletonArray() {
        BinarySearch single = new BinarySearch(new int[]{42});
        assertThat(single.indexOf(42)).isEqualTo(0);
    }
}
