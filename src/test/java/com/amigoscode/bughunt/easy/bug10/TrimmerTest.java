package com.amigoscode.bughunt.easy.bug10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrimmerTest {

    private final Trimmer t = new Trimmer();

    @Test
    void trimsAndLowercases() {
        assertThat(t.cleanup("  Hello World  ")).isEqualTo("hello world");
    }

    @Test
    void handlesAlreadyClean() {
        assertThat(t.cleanup("java")).isEqualTo("java");
    }
}
