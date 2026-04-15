package com.amigoscode.bughunt.easy.bug05;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FactorialTest {

    private final Factorial f = new Factorial();

    @Test
    void factorialOfFive() {
        assertThat(f.factorial(5)).isEqualTo(120L);
    }

    @Test
    void factorialOfTwenty() {
        assertThat(f.factorial(20)).isEqualTo(2432902008176640000L);
    }
}
