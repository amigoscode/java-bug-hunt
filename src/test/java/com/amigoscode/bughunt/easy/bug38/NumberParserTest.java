package com.amigoscode.bughunt.easy.bug38;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberParserTest {

    private final NumberParser parser = new NumberParser(0);

    @Test
    void parsesPlainNumber() {
        assertThat(parser.parse("42")).isEqualTo(42);
    }

    @Test
    void parsesNumberWithSurroundingWhitespace() {
        assertThat(parser.parse("  42  ")).isEqualTo(42);
    }

    @Test
    void parsesNumberWithLeadingPlus() {
        assertThat(parser.parse("+7")).isEqualTo(7);
    }
}
