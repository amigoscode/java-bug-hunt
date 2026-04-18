package com.amigoscode.bughunt.medium.bug85;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Builds a single CSV line from a sequence of field values.
 * Null fields should be rendered as empty strings between delimiters
 * (e.g. "a,,c"), not as the literal text "null".
 *
 * <p>BUG: {@link #build(String...)} uses {@link String#join(CharSequence, CharSequence...)}
 * which converts {@code null} elements to the literal string {@code "null"}.
 * The result is {@code "a,null,c"} instead of the expected {@code "a,,c"}.
 */
public class CsvLine {

    private final String delimiter;

    /**
     * Creates a CsvLine builder with the default comma delimiter.
     */
    public CsvLine() {
        this(",");
    }

    /**
     * Creates a CsvLine builder with the specified delimiter.
     *
     * @param delimiter the field separator
     */
    public CsvLine(String delimiter) {
        this.delimiter = Objects.requireNonNull(delimiter, "delimiter must not be null");
    }

    /**
     * Builds a CSV line from the given fields.
     *
     * <p>Null fields should produce empty segments (e.g. {@code "a,,c"}).
     *
     * <p>BUG: {@code String.join} converts null to the literal text "null".
     *
     * @param fields the field values (may contain nulls)
     * @return the joined CSV line
     */
    public String build(String... fields) {
        Objects.requireNonNull(fields, "fields array must not be null");
        // BUG: String.join turns null into "null" literal
        return String.join(delimiter, fields);
    }

    /**
     * Builds a CSV line from a list of fields.
     *
     * <p>BUG: same issue -- delegates to the varargs build method.
     *
     * @param fields the list of field values (may contain nulls)
     * @return the joined CSV line
     */
    public String buildFromList(List<String> fields) {
        Objects.requireNonNull(fields, "fields list must not be null");
        return build(fields.toArray(new String[0]));
    }

    /**
     * Counts the number of non-null, non-empty fields in the given values.
     *
     * @param fields the field values
     * @return the count of populated fields
     */
    public int countPopulated(String... fields) {
        Objects.requireNonNull(fields, "fields array must not be null");
        int count = 0;
        for (String f : fields) {
            if (f != null && !f.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns an unmodifiable list of the fields, replacing nulls with
     * empty strings.
     *
     * @param fields the raw field values
     * @return a list with nulls replaced by ""
     */
    public List<String> sanitise(String... fields) {
        Objects.requireNonNull(fields, "fields array must not be null");
        List<String> result = new ArrayList<>(fields.length);
        for (String f : fields) {
            result.add(f == null ? "" : f);
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Returns the delimiter used by this builder.
     */
    public String delimiter() {
        return delimiter;
    }

    @Override
    public String toString() {
        return "CsvLine{delimiter='" + delimiter + "'}";
    }
}
