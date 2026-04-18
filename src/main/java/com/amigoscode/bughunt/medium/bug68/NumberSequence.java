package com.amigoscode.bughunt.medium.bug68;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A sequence of integers from 0 (inclusive) to max (exclusive).
 * Implements both Iterable and Iterator, allowing use in enhanced for-loops.
 *
 * BUG: The class returns {@code this} as its own iterator, meaning the
 * internal cursor is shared across all iterations. After the first
 * iteration completes, subsequent iterations produce no elements
 * because {@code current} is already at {@code max}.
 */
public class NumberSequence implements Iterable<Integer>, Iterator<Integer> {

    private int current = 0;
    private final int max;
    private final String label;

    /**
     * Creates a number sequence from 0 to max (exclusive).
     *
     * @param max   the upper bound (exclusive)
     * @param label a descriptive label for this sequence
     */
    public NumberSequence(int max, String label) {
        if (max < 0) {
            throw new IllegalArgumentException("max must be non-negative");
        }
        this.max = max;
        this.label = label;
    }

    /**
     * Convenience constructor with a default label.
     */
    public NumberSequence(int max) {
        this(max, "Sequence[0.." + max + ")");
    }

    /**
     * Returns an iterator over the sequence.
     * BUG: returns {@code this}, so the cursor is never reset.
     */
    @Override
    public Iterator<Integer> iterator() {
        return this; // BUG: should return a fresh iterator each time
    }

    @Override
    public boolean hasNext() {
        return current < max;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in " + label);
        }
        return current++;
    }

    /**
     * Returns the upper bound of this sequence.
     */
    public int max() {
        return max;
    }

    /**
     * Returns the label for this sequence.
     */
    public String label() {
        return label;
    }

    /**
     * Collects all elements of the sequence into a list by iterating over it.
     *
     * @return a list of all values in the sequence
     */
    public List<Integer> toList() {
        List<Integer> result = new ArrayList<>();
        for (int value : this) {
            result.add(value);
        }
        return result;
    }

    /**
     * Computes the sum of all elements by iterating over the sequence.
     *
     * @return the sum of all values
     */
    public int sum() {
        int total = 0;
        for (int value : this) {
            total += value;
        }
        return total;
    }
}
