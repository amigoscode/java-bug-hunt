package com.amigoscode.bughunt.medium.bug94;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Merges word-frequency maps from multiple sources into a single
 * consolidated frequency map.
 *
 * Useful for combining word counts from different documents or
 * text-processing pipelines.
 */
public class WordMerger {

    private final Map<String, Integer> merged = new HashMap<>();
    private final String label;

    /**
     * Creates a new WordMerger with the given label.
     *
     * @param label a descriptive label for this merger
     */
    public WordMerger(String label) {
        this.label = Objects.requireNonNull(label, "label must not be null");
    }

    /**
     * Merges word counts from the given source map into the consolidated map.
     * If a word already exists, its counts are summed.
     *
     * BUG: Uses {@code Map.merge(key, value, Integer::sum)}. The merge method
     * throws {@code NullPointerException} if the value argument is null.
     * If the source map contains a key mapped to null, this will blow up.
     *
     * @param source the word-frequency map to merge in
     * @throws NullPointerException if any value in source is null
     */
    public void mergeFrom(Map<String, Integer> source) {
        Objects.requireNonNull(source, "source must not be null");
        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            // BUG: merge throws NPE if entry.getValue() is null
            merged.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    /**
     * Returns the count for a specific word, or 0 if not present.
     *
     * @param word the word to look up
     * @return the accumulated count
     */
    public int countOf(String word) {
        return merged.getOrDefault(word, 0);
    }

    /**
     * Returns the total number of distinct words in the merged map.
     */
    public int distinctWords() {
        return merged.size();
    }

    /**
     * Returns the total count across all words.
     */
    public long totalCount() {
        long sum = 0;
        for (int count : merged.values()) {
            sum += count;
        }
        return sum;
    }

    /**
     * Returns an unmodifiable view of the merged word-frequency map.
     */
    public Map<String, Integer> result() {
        return Collections.unmodifiableMap(merged);
    }

    /**
     * Returns the label of this merger.
     */
    public String label() {
        return label;
    }

    @Override
    public String toString() {
        return "WordMerger{label='" + label + "', words=" + merged.size() + "}";
    }
}
