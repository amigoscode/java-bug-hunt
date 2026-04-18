package com.amigoscode.bughunt.medium.bug99;

import java.util.Objects;

/**
 * An HTML formatter that wraps text in HTML tags.
 * Extends {@link Formatter} to provide HTML-specific formatting.
 *
 * BUG: The {@code format} and {@code formatBlock} methods are declared
 * {@code static}, which means they <em>hide</em> the parent's static methods
 * rather than overriding them. When called through a {@code Formatter}
 * reference, the parent's version runs instead. This is because static
 * methods are resolved at compile time by reference type, not at runtime
 * by object type.
 */
public class HtmlFormatter extends Formatter {

    private final boolean escapeHtml;

    /**
     * Creates an HTML formatter.
     *
     * @param name       the formatter's descriptive name
     * @param escapeHtml whether to escape special HTML characters
     */
    public HtmlFormatter(String name, boolean escapeHtml) {
        super(name);
        this.escapeHtml = escapeHtml;
    }

    /**
     * Formats the input as an HTML paragraph.
     *
     * BUG: This is a static method that <em>hides</em> (not overrides)
     * {@code Formatter.format()}. Calling via a {@code Formatter} reference
     * dispatches to the parent's method instead.
     *
     * @param input the text to format
     * @return the text wrapped in {@code <p>} tags
     */
    public static String format(String input) {
        Objects.requireNonNull(input, "input must not be null");
        return "<p>" + input + "</p>";
    }

    /**
     * Formats the input as an HTML div block.
     *
     * BUG: Also static -- hides the parent's {@code formatBlock()}.
     *
     * @param input the text to wrap
     * @return the text wrapped in {@code <div>} tags
     */
    public static String formatBlock(String input) {
        Objects.requireNonNull(input, "input must not be null");
        return "<div>" + input + "</div>";
    }

    /**
     * Returns whether this formatter escapes HTML characters.
     *
     * @return true if HTML escaping is enabled
     */
    public boolean isEscapeHtml() {
        return escapeHtml;
    }

    /**
     * Returns the type of this formatter.
     *
     * @return "html"
     */
    @Override
    public String type() {
        return "html";
    }

    /**
     * Formats the input as an HTML heading at the given level.
     *
     * @param input the heading text
     * @param level the heading level (1-6)
     * @return the text wrapped in heading tags
     */
    public static String formatHeading(String input, int level) {
        Objects.requireNonNull(input, "input must not be null");
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("level must be between 1 and 6");
        }
        return "<h" + level + ">" + input + "</h" + level + ">";
    }

    @Override
    public String toString() {
        return "HtmlFormatter{name='" + getName()
                + "', escapeHtml=" + escapeHtml + "}";
    }
}
