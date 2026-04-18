package com.amigoscode.bughunt.medium.bug74;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A text processing pipeline that filters and transforms string items.
 * Tracks how many items were inspected during processing.
 */
public class ProcessingPipeline {

    private int count;

    /**
     * Finds the first item that starts with the given prefix.
     * Also counts how many items were inspected via peek().
     *
     * BUG: peek() is not guaranteed to execute for all elements.
     * When the terminal operation is short-circuiting (like findFirst),
     * the stream may stop processing as soon as a match is found,
     * so peek() only runs for the elements actually consumed -- not
     * the entire stream.
     *
     * @param items  the list of items to search
     * @param prefix the prefix to match
     * @return the first matching item, or empty if none match
     */
    public Optional<String> firstMatching(List<String> items, String prefix) {
        Objects.requireNonNull(items, "items must not be null");
        Objects.requireNonNull(prefix, "prefix must not be null");
        count = 0;
        return items.stream()
                .peek(i -> count++)  // BUG: not called for all elements
                .filter(i -> i.startsWith(prefix))
                .findFirst();
    }

    /**
     * Returns the number of items that were processed (inspected)
     * during the last call to firstMatching().
     *
     * @return the count of processed items
     */
    public int lastProcessedCount() {
        return count;
    }

    /**
     * Transforms all items to uppercase and returns them.
     *
     * @param items the list of items
     * @return uppercase versions of all items
     */
    public List<String> toUpperCase(List<String> items) {
        return items.stream()
                .map(String::toUpperCase)
                .toList();
    }

    /**
     * Filters items by minimum length and returns them sorted.
     *
     * @param items     the list of items
     * @param minLength the minimum length (inclusive)
     * @return filtered and sorted items
     */
    public List<String> filterByLength(List<String> items, int minLength) {
        return items.stream()
                .filter(i -> i.length() >= minLength)
                .sorted()
                .toList();
    }

    /**
     * Counts items that contain the given substring.
     *
     * @param items     the list of items
     * @param substring the substring to search for
     * @return the count of matching items
     */
    public long countContaining(List<String> items, String substring) {
        Objects.requireNonNull(substring, "substring must not be null");
        return items.stream()
                .filter(i -> i.contains(substring))
                .count();
    }
}
