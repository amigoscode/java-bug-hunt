package com.amigoscode.bughunt.easy.bug44;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FibonacciTest {

    private final Fibonacci fib = new Fibonacci(20);

    @Test
    void fibZeroIsZero() {
        assertThat(fib.compute(0)).isEqualTo(0L);
    }

    @Test
    void fibOneIsOne() {
        assertThat(fib.compute(1)).isEqualTo(1L);
    }

    @Test
    void fibTwoIsOne() {
        assertThat(fib.compute(2)).isEqualTo(1L);
    }

    @Test
    void fibFiveIsFive() {
        assertThat(fib.compute(5)).isEqualTo(5L);
    }

    @Test
    void fibTenIsFiftyFive() {
        assertThat(fib.compute(10)).isEqualTo(55L);
    }
}
