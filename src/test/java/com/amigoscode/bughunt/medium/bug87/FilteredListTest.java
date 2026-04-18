package com.amigoscode.bughunt.medium.bug87;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilteredListTest {

    private final FilteredList filter = new FilteredList();

    @Test
    void removeByPrefixShouldRemoveMatchingElements() {
        List<String> items = new ArrayList<>(Arrays.asList("test-1", "prod-1", "test-2", "prod-2"));

        // BUG: throws IllegalStateException because remove() is called without next()
        List<String> result = filter.removeByPrefix(items, "test");

        assertThat(result).containsExactly("prod-1", "prod-2");
    }

    @Test
    void removeByPrefixShouldLeaveNonMatchingUntouched() {
        List<String> items = new ArrayList<>(Arrays.asList("alpha", "beta", "gamma"));

        List<String> result = filter.removeByPrefix(items, "x");

        assertThat(result).containsExactly("alpha", "beta", "gamma");
    }

    @Test
    void removeShortShouldRemoveShortStrings() {
        List<String> items = new ArrayList<>(Arrays.asList("hi", "hello", "hey", "howdy"));

        List<String> result = filter.removeShort(items, 4);

        assertThat(result).containsExactly("hello", "howdy");
    }

    @Test
    void keepNonBlankShouldFilterBlanks() {
        List<String> items = Arrays.asList("a", "", "b", "  ", null, "c");

        List<String> result = filter.keepNonBlank(items);

        assertThat(result).containsExactly("a", "b", "c");
    }

    @Test
    void countByPrefixShouldCountMatches() {
        List<String> items = Arrays.asList("err-1", "info-1", "err-2", "warn-1");

        int count = filter.countByPrefix(items, "err");

        assertThat(count).isEqualTo(2);
    }

    @Test
    void snapshotShouldReturnCopy() {
        List<String> items = new ArrayList<>(Arrays.asList("a", "b"));

        List<String> snap = filter.snapshot(items);

        assertThat(snap).containsExactly("a", "b");
    }
}
