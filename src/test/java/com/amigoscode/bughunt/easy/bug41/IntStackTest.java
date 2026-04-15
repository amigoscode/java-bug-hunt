package com.amigoscode.bughunt.easy.bug41;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntStackTest {

    @Test
    void popReturnsLastPushed() {
        IntStack s = new IntStack("s");
        s.push(1);
        s.push(2);
        s.push(3);

        assertThat(s.pop()).isEqualTo(3);
        assertThat(s.pop()).isEqualTo(2);
        assertThat(s.pop()).isEqualTo(1);
    }

    @Test
    void peekReturnsTopWithoutRemoving() {
        IntStack s = new IntStack("s");
        s.push(10);
        s.push(20);

        assertThat(s.peek()).isEqualTo(20);
        assertThat(s.size()).isEqualTo(2);
    }
}
