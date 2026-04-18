package com.amigoscode.bughunt.medium.bug99;

import java.util.Objects;

/**
 * A base text formatter that provides plain-text formatting utilities.
 * Subclasses are expected to override behavior for specific output formats.
 *
 * Used by the rendering pipeline to convert raw text into display-ready strings.
 */
public class Formatter {

    private final String name;

    /**
     * Creates a formatter with the given name.
     *
     * @param name the formatter's descriptive name
     */
    public Formatter(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    /**
     * Formats the input string as plain text (returns it unchanged).
     *
     * BUG: This is a {@code static} method. Static methods cannot be
     * overridden -- they are resolved at compile time based on the
     * reference type, not the runtime type. When called through a
     * {@code Formatter} reference pointing to an {@code HtmlFormatter},
     * this method runs instead of the subclass version.
     *
     * @param input the text to format
     * @return the input unchanged
     */
    public static String format(String input) {
        Objects.requireNonNull(input, "input must not be null");
        return input;
    }

    /**
     * Wraps the input in a simple text border.
     *
     * BUG: Also static -- same hiding problem.
     *
     * @param input the text to wrap
     * @return the bordered text
     */
    public static String formatBlock(String input) {
        Objects.requireNonNull(input, "input must not be null");
        return "--- " + input + " ---";
    }

    /**
     * Returns the name of this formatter.
     *
     * @return the formatter name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a description of the formatter type.
     *
     * @return a type description string
     */
    public String type() {
        return "plain";
    }

    @Override
    public String toString() {
        return "Formatter{name='" + name + "', type='" + type() + "'}";
    }
}
