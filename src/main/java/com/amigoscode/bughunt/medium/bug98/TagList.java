package com.amigoscode.bughunt.medium.bug98;

import java.util.List;
import java.util.Objects;

/**
 * An immutable collection of tags used for categorizing content.
 * Supports querying, filtering, and formatting tags.
 *
 * Used by the content management system to organize articles and media.
 */
public class TagList {

    private final List<String> tags;

    /**
     * Creates a tag list from the given tags.
     *
     * BUG: {@code List.of()} does not allow {@code null} elements.
     * If any tag is {@code null}, the constructor throws a
     * {@code NullPointerException} instead of silently filtering it out.
     *
     * @param tags the tags to store
     * @throws NullPointerException if any tag is null (due to List.of)
     */
    public TagList(String... tags) {
        // BUG: List.of throws NPE if any element is null
        this.tags = List.of(tags);
    }

    /**
     * Returns the number of tags.
     *
     * @return the tag count
     */
    public int size() {
        return tags.size();
    }

    /**
     * Checks if a given tag is present in the list.
     *
     * @param tag the tag to search for
     * @return true if the tag exists
     */
    public boolean contains(String tag) {
        return tags.contains(tag);
    }

    /**
     * Returns the tag at the given index.
     *
     * @param index the zero-based index
     * @return the tag at that position
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public String get(int index) {
        return tags.get(index);
    }

    /**
     * Returns all tags as a comma-separated string.
     *
     * @return a formatted string of tags
     */
    public String formatted() {
        return String.join(", ", tags);
    }

    /**
     * Returns all tags that start with the given prefix.
     *
     * @param prefix the prefix to match
     * @return a list of matching tags
     */
    public List<String> startingWith(String prefix) {
        Objects.requireNonNull(prefix, "prefix must not be null");
        return tags.stream()
                .filter(t -> t.startsWith(prefix))
                .toList();
    }

    /**
     * Returns an unmodifiable view of all tags.
     *
     * @return the list of tags
     */
    public List<String> all() {
        return tags;
    }

    /**
     * Returns the number of tags that have more characters than the given length.
     *
     * @param length the minimum length (exclusive)
     * @return the count of tags longer than the given length
     */
    public long countLongerThan(int length) {
        return tags.stream()
                .filter(t -> t.length() > length)
                .count();
    }

    @Override
    public String toString() {
        return "TagList{tags=" + tags + "}";
    }
}
