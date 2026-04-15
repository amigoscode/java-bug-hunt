package com.amigoscode.bughunt.easy.bug01;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {

    private final Greeter greeter = new Greeter();

    @Test
    void greetsNamedUser() {
        assertThat(greeter.greet("ada")).isEqualTo("Hello, ADA!");
    }

    @Test
    void greetsNullAsGuest() {
        assertThat(greeter.greet(null)).isEqualTo("Hello, GUEST!");
    }
}
