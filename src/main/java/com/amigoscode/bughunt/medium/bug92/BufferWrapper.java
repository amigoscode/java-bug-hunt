package com.amigoscode.bughunt.medium.bug92;

import java.util.Objects;

/**
 * A wrapper around StringBuilder that provides pre-sized character buffers
 * and convenience methods for common string-building operations.
 *
 * Used to build formatted text content with a pre-allocated buffer.
 */
public class BufferWrapper {

    private final StringBuilder buffer;
    private final int requestedSize;
    private final String name;

    /**
     * Creates a new BufferWrapper with the specified initial size.
     *
     * BUG: The constructor passes {@code size} to {@code new StringBuilder(size)},
     * believing this pre-fills the buffer with {@code size} characters. However,
     * {@code StringBuilder(int)} sets the internal <em>capacity</em> (allocated
     * storage), not the <em>length</em> (number of characters). The resulting
     * StringBuilder has length 0, so calling {@code charAt(0)} throws
     * {@code StringIndexOutOfBoundsException}.
     *
     * @param name a label for this buffer
     * @param size the desired initial size (intended to pre-fill characters)
     * @throws IllegalArgumentException if size is negative
     */
    public BufferWrapper(String name, int size) {
        Objects.requireNonNull(name, "name must not be null");
        if (size < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        this.name = name;
        this.requestedSize = size;
        // BUG: sets capacity, not length -- buffer is still empty
        this.buffer = new StringBuilder(size);
    }

    /**
     * Returns the character at the given index in the buffer.
     *
     * @param index the index of the character to return
     * @return the character at the given index
     * @throws StringIndexOutOfBoundsException if index is out of range
     */
    public char charAt(int index) {
        return buffer.charAt(index);
    }

    /**
     * Returns the current length (number of characters) in the buffer.
     */
    public int length() {
        return buffer.length();
    }

    /**
     * Returns the size that was requested when this buffer was created.
     */
    public int requestedSize() {
        return requestedSize;
    }

    /**
     * Appends text to the buffer.
     *
     * @param text the text to append
     * @return this BufferWrapper for chaining
     */
    public BufferWrapper append(String text) {
        if (text != null) {
            buffer.append(text);
        }
        return this;
    }

    /**
     * Appends a character repeated {@code count} times.
     *
     * @param ch    the character to repeat
     * @param count how many times to repeat it
     * @return this BufferWrapper for chaining
     */
    public BufferWrapper appendRepeated(char ch, int count) {
        for (int i = 0; i < count; i++) {
            buffer.append(ch);
        }
        return this;
    }

    /**
     * Returns the buffer content as a String.
     */
    public String content() {
        return buffer.toString();
    }

    /**
     * Returns the name of this buffer.
     */
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "BufferWrapper{name='" + name + "', requested=" + requestedSize
                + ", length=" + buffer.length() + "}";
    }
}
