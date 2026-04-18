package com.amigoscode.bughunt.medium.bug76;

import com.amigoscode.bughunt.medium.bug76.ColorParser.Color;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColorParserTest {

    @Test
    void parseLowercaseInputShouldReturnMatchingColor() {
        ColorParser parser = new ColorParser("WHITE");

        // Users naturally type lowercase -- "red", "blue", etc.
        Color result = parser.parse("red");

        assertThat(result).isEqualTo(Color.RED);
    }

    @Test
    void parseMixedCaseInputShouldReturnMatchingColor() {
        ColorParser parser = new ColorParser("WHITE");

        Color result = parser.parse("Green");

        assertThat(result).isEqualTo(Color.GREEN);
    }

    @Test
    void hexOfLowercaseInputShouldReturnCorrectHex() {
        ColorParser parser = new ColorParser("WHITE");

        String hex = parser.hexOf("blue");

        assertThat(hex).isEqualTo("#0000FF");
    }

    @Test
    void isWarmShouldReturnTrueForYellow() {
        ColorParser parser = new ColorParser("WHITE");

        boolean warm = parser.isWarm("yellow");

        assertThat(warm).isTrue();
    }

    @Test
    void isCoolShouldReturnTrueForCyan() {
        ColorParser parser = new ColorParser("WHITE");

        boolean cool = parser.isCool("cyan");

        assertThat(cool).isTrue();
    }
}
