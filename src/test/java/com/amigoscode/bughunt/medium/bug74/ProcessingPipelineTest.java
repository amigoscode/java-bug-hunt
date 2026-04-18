package com.amigoscode.bughunt.medium.bug74;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessingPipelineTest {

    private final ProcessingPipeline pipeline = new ProcessingPipeline();

    @Test
    void firstMatchingShouldCountAllItemsEvenWhenMatchFoundEarly() {
        List<String> items = List.of(
                "apple",    // match -- starts with "a"
                "banana",
                "avocado",
                "cherry",
                "apricot"
        );

        Optional<String> result = pipeline.firstMatching(items, "a");

        // The match is found at the first element
        assertThat(result).contains("apple");

        // We expect the pipeline to have inspected ALL items in the list
        assertThat(pipeline.lastProcessedCount()).isEqualTo(5);
    }

    @Test
    void firstMatchingShouldCountAllItemsWhenMatchIsLast() {
        List<String> items = List.of(
                "banana",
                "cherry",
                "date",
                "elderberry",
                "apple"     // match at the end
        );

        Optional<String> result = pipeline.firstMatching(items, "a");

        assertThat(result).contains("apple");

        // All items should have been inspected
        assertThat(pipeline.lastProcessedCount()).isEqualTo(5);
    }

    @Test
    void firstMatchingShouldCountAllItemsWhenNoMatch() {
        List<String> items = List.of("banana", "cherry", "date");

        Optional<String> result = pipeline.firstMatching(items, "z");

        assertThat(result).isEmpty();

        // Even with no match, all items should be counted
        assertThat(pipeline.lastProcessedCount()).isEqualTo(3);
    }

    @Test
    void firstMatchingShouldReturnFirstMatchingItem() {
        List<String> items = List.of("banana", "apple", "avocado");

        Optional<String> result = pipeline.firstMatching(items, "a");

        assertThat(result).contains("apple");
    }

    @Test
    void filterByLengthShouldReturnSortedResults() {
        List<String> items = List.of("hi", "hello", "hey", "howdy", "hola");

        List<String> result = pipeline.filterByLength(items, 4);

        assertThat(result).containsExactly("hello", "hola", "howdy");
    }
}
