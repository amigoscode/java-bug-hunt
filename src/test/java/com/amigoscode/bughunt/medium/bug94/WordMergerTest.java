package com.amigoscode.bughunt.medium.bug94;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WordMergerTest {

    @Test
    void mergeShouldHandleNullValuesGracefully() {
        WordMerger merger = new WordMerger("null-safe");

        // Source map with a null value -- perhaps from a parser that
        // records a word but hasn't counted it yet
        Map<String, Integer> source = new HashMap<>();
        source.put("hello", 5);
        source.put("world", null); // null count -- should be treated as 0

        merger.mergeFrom(source);

        assertThat(merger.countOf("hello")).isEqualTo(5);
        assertThat(merger.countOf("world")).isEqualTo(0);
    }

    @Test
    void mergeShouldSumCountsFromMultipleSources() {
        WordMerger merger = new WordMerger("sum-test");

        merger.mergeFrom(Map.of("java", 10, "python", 5));
        merger.mergeFrom(Map.of("java", 3, "go", 7));

        assertThat(merger.countOf("java")).isEqualTo(13);
        assertThat(merger.countOf("python")).isEqualTo(5);
        assertThat(merger.countOf("go")).isEqualTo(7);
    }

    @Test
    void distinctWordsShouldCountUniqueKeys() {
        WordMerger merger = new WordMerger("distinct");

        merger.mergeFrom(Map.of("a", 1, "b", 2, "c", 3));

        assertThat(merger.distinctWords()).isEqualTo(3);
    }

    @Test
    void totalCountShouldSumAllValues() {
        WordMerger merger = new WordMerger("total");

        merger.mergeFrom(Map.of("x", 10, "y", 20));

        assertThat(merger.totalCount()).isEqualTo(30);
    }

    @Test
    void mergeFromMapWithAllNullValuesShouldNotThrow() {
        WordMerger merger = new WordMerger("all-nulls");

        Map<String, Integer> source = new HashMap<>();
        source.put("alpha", null);
        source.put("beta", null);

        merger.mergeFrom(source);

        assertThat(merger.countOf("alpha")).isEqualTo(0);
        assertThat(merger.countOf("beta")).isEqualTo(0);
    }
}
