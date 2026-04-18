package com.amigoscode.bughunt.medium.bug77;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharacterAnalyzerTest {

    @Test
    void containsCharShouldReturnTrueForPresentCharacter() {
        CharacterAnalyzer analyzer = new CharacterAnalyzer("hello");

        // 'h' is clearly in "hello"
        boolean result = analyzer.containsChar('h');

        assertThat(result).isTrue();
    }

    @Test
    void containsCharShouldReturnTrueForLastCharacter() {
        CharacterAnalyzer analyzer = new CharacterAnalyzer("world");

        boolean result = analyzer.containsChar('d');

        assertThat(result).isTrue();
    }

    @Test
    void containsAllShouldReturnTrueWhenAllCharsPresent() {
        CharacterAnalyzer analyzer = new CharacterAnalyzer("abcdef");

        boolean result = analyzer.containsAll("ace");

        assertThat(result).isTrue();
    }

    @Test
    void uniqueCharactersShouldContainCharacterValues() {
        CharacterAnalyzer analyzer = new CharacterAnalyzer("abc");

        // The set should contain Character 'a', not Integer 97
        assertThat(analyzer.uniqueCharacters()).contains('a');
    }

    @Test
    void distinctCountShouldCountUniqueCharacters() {
        CharacterAnalyzer analyzer = new CharacterAnalyzer("banana");

        // 'b', 'a', 'n' -- 3 distinct characters
        assertThat(analyzer.distinctCount()).isEqualTo(3);
    }
}
