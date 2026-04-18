package com.amigoscode.bughunt.medium.bug87;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Provides utilities to filter elements from a list of strings based on
 * various criteria: by prefix, by minimum length, or by a custom predicate.
 *
 * <p>BUG: In {@link #removeByPrefix(List, String)}, the iterator calls
 * {@code it.remove()} after checking {@code it.hasNext()} but without ever
 * calling {@code it.next()}. The {@code Iterator} contract requires
 * {@code next()} to be called before each {@code remove()}, so this throws
 * {@link IllegalStateException}.
 */
public class FilteredList {

    /**
     * Removes all elements that start with the given prefix.
     *
     * <p>BUG: calls {@code it.remove()} without calling {@code it.next()} first.
     *
     * @param items  the mutable list to filter in place
     * @param prefix the prefix to match
     * @return the modified list (same reference)
     */
    public List<String> removeByPrefix(List<String> items, String prefix) {
        Objects.requireNonNull(items, "items must not be null");
        Objects.requireNonNull(prefix, "prefix must not be null");

        Iterator<String> it = items.iterator();
        while (it.hasNext()) {
            // BUG: using hasNext() instead of next() -- remove() requires next() first
            if (it.hasNext() && items.get(0).startsWith(prefix)) {
                it.remove();  // throws IllegalStateException
            }
        }
        return items;
    }

    /**
     * Removes all elements shorter than the specified minimum length.
     *
     * @param items     the mutable list to filter in place
     * @param minLength the minimum acceptable length
     * @return the modified list (same reference)
     */
    public List<String> removeShort(List<String> items, int minLength) {
        Objects.requireNonNull(items, "items must not be null");
        Iterator<String> it = items.iterator();
        while (it.hasNext()) {
            String element = it.next();
            if (element.length() < minLength) {
                it.remove();
            }
        }
        return items;
    }

    /**
     * Returns a new list containing only elements that are not blank.
     *
     * @param items the source list
     * @return a new list with blank entries removed
     */
    public List<String> keepNonBlank(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        List<String> result = new ArrayList<>();
        for (String item : items) {
            if (item != null && !item.isBlank()) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Returns the count of elements matching the given prefix.
     *
     * @param items  the list to inspect
     * @param prefix the prefix to match
     * @return the number of matching elements
     */
    public int countByPrefix(List<String> items, String prefix) {
        Objects.requireNonNull(items, "items must not be null");
        Objects.requireNonNull(prefix, "prefix must not be null");
        int count = 0;
        for (String item : items) {
            if (item != null && item.startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns an unmodifiable snapshot of the list.
     *
     * @param items the source list
     * @return an unmodifiable copy
     */
    public List<String> snapshot(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        return Collections.unmodifiableList(new ArrayList<>(items));
    }
}
