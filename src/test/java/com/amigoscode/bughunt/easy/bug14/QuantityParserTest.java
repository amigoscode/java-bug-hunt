package com.amigoscode.bughunt.easy.bug14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuantityParserTest {

    @Test
    void parsesValidNumber() {
        QuantityParser p = new QuantityParser();
        assertThat(p.parseOrDefault("42", 0)).isEqualTo(42);
        assertThat(p.totalErrors()).isZero();
    }

    @Test
    void tracksEveryInvalidInput() {
        QuantityParser p = new QuantityParser();
        p.parseOrDefault("abc", 0);
        p.parseOrDefault("xyz", 0);

        assertThat(p.totalErrors()).isEqualTo(2);
        assertThat(p.invalidInputs()).containsExactly("abc", "xyz");
    }

    @Test
    void sumAllFallsBackForInvalid() {
        QuantityParser p = new QuantityParser();
        int sum = p.sumAll(List.of("1", "nope", "3"), 0);

        assertThat(sum).isEqualTo(4);
        assertThat(p.totalErrors()).isEqualTo(1);
    }
}
