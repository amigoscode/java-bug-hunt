package com.amigoscode.bughunt.medium.bug76;

import java.util.Objects;

/**
 * Parses user-supplied colour strings into a type-safe {@link Color} enum.
 * Supports lookup by name and provides utilities for working with colour
 * values such as hex codes and whether a colour is considered "warm" or "cool".
 */
public class ColorParser {

    /**
     * The set of supported colours.
     */
    public enum Color {
        RED("#FF0000"),
        GREEN("#00FF00"),
        BLUE("#0000FF"),
        YELLOW("#FFFF00"),
        CYAN("#00FFFF"),
        MAGENTA("#FF00FF"),
        WHITE("#FFFFFF"),
        BLACK("#000000");

        private final String hex;

        Color(String hex) {
            this.hex = hex;
        }

        /**
         * Returns the hex string for this colour (e.g. "#FF0000").
         */
        public String hex() {
            return hex;
        }
    }

    private final String defaultColorName;

    /**
     * Creates a parser with a fallback colour name used when input is blank.
     *
     * @param defaultColorName the fallback colour name (must be a valid enum constant)
     */
    public ColorParser(String defaultColorName) {
        this.defaultColorName = Objects.requireNonNull(
                defaultColorName, "defaultColorName must not be null");
    }

    /**
     * Parses a user-supplied string into a {@link Color}.
     * <p>
     * BUG: {@code Color.valueOf(input)} is case-sensitive and expects
     * the exact enum constant name (e.g. "RED"). User input like "red"
     * or "Red" will throw an {@link IllegalArgumentException}.
     *
     * @param input the colour name typed by the user
     * @return the matching {@link Color}
     * @throws IllegalArgumentException if the input does not match any constant
     */
    public Color parse(String input) {
        Objects.requireNonNull(input, "input must not be null");
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            trimmed = defaultColorName;
        }
        // BUG: valueOf is case-sensitive -- "red" != "RED"
        return Color.valueOf(trimmed);
    }

    /**
     * Returns the hex code for the given user-supplied colour name.
     *
     * @param input the colour name
     * @return the hex string (e.g. "#FF0000")
     */
    public String hexOf(String input) {
        return parse(input).hex();
    }

    /**
     * Determines whether the parsed colour is considered "warm".
     * Warm colours are RED, YELLOW, and MAGENTA.
     *
     * @param input the colour name
     * @return true if the colour is warm
     */
    public boolean isWarm(String input) {
        Color color = parse(input);
        return color == Color.RED || color == Color.YELLOW || color == Color.MAGENTA;
    }

    /**
     * Determines whether the parsed colour is considered "cool".
     * Cool colours are GREEN, BLUE, and CYAN.
     *
     * @param input the colour name
     * @return true if the colour is cool
     */
    public boolean isCool(String input) {
        Color color = parse(input);
        return color == Color.GREEN || color == Color.BLUE || color == Color.CYAN;
    }

    /**
     * Returns the default colour name used for blank input.
     */
    public String defaultColorName() {
        return defaultColorName;
    }

    @Override
    public String toString() {
        return "ColorParser{default='" + defaultColorName + "'}";
    }
}
