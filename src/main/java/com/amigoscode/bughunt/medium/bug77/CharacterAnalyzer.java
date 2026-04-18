package com.amigoscode.bughunt.medium.bug77;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Analyses characters in a string -- finding unique characters, checking
 * for character membership, and counting distinct characters.
 *
 * Uses Java's {@code String.chars()} stream to process characters.
 */
public class CharacterAnalyzer {

    private final String text;

    /**
     * Creates an analyser for the given text.
     *
     * @param text the text to analyse (must not be null)
     */
    public CharacterAnalyzer(String text) {
        this.text = Objects.requireNonNull(text, "text must not be null");
    }

    /**
     * Returns the set of unique characters in the text.
     * <p>
     * BUG: {@code String.chars()} returns an {@code IntStream}, not a
     * {@code Stream<Character>}. Calling {@code boxed()} on an IntStream
     * produces {@code Stream<Integer>}, so the collected set is actually
     * {@code Set<Integer>}, not {@code Set<Character>}. The return type
     * is erased to {@code Set<Object>} so it compiles, but later
     * comparisons with {@code char}/{@code Character} values fail due
     * to type mismatch (Integer vs Character).
     *
     * @return a set of unique characters (actually Integers due to the bug)
     */
    @SuppressWarnings("unchecked")
    public Set<Object> uniqueCharacters() {
        // BUG: boxed() on IntStream gives Stream<Integer>, not Stream<Character>
        return text.chars()
                .boxed()
                .collect(Collectors.toSet());
    }

    /**
     * Checks whether the text contains the given character.
     * <p>
     * BUG: delegates to {@link #uniqueCharacters()}, which returns a
     * {@code Set<Integer>}. The char argument is autoboxed to a
     * {@code Character}, and {@code Set<Integer>.contains(Character)}
     * always returns false because Integer and Character are different types.
     *
     * @param c the character to look for
     * @return true if the text contains c (but always returns false due to bug)
     */
    public boolean containsChar(char c) {
        return uniqueCharacters().contains(c); // Character vs Integer mismatch
    }

    /**
     * Returns the number of distinct characters in the text.
     */
    public int distinctCount() {
        return uniqueCharacters().size();
    }

    /**
     * Checks whether all characters in {@code chars} appear in the text.
     *
     * @param chars the characters to check
     * @return true if every character in chars is present in the text
     */
    public boolean containsAll(String chars) {
        Objects.requireNonNull(chars, "chars must not be null");
        for (int i = 0; i < chars.length(); i++) {
            if (!containsChar(chars.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the original text being analysed.
     */
    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return "CharacterAnalyzer{text='" + text + "', distinct=" + distinctCount() + "}";
    }
}
