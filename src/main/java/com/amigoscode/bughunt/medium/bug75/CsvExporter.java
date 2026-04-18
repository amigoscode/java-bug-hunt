package com.amigoscode.bughunt.medium.bug75;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Exports data rows as CSV-formatted strings.
 * Each row contains a name and a numeric score.
 */
public class CsvExporter {

    private static final String HEADER = "name,score";

    /**
     * Represents a single data row to export.
     *
     * @param name  the entry name
     * @param score the numeric score
     */
    public record DataRow(String name, double score) {

        public DataRow {
            Objects.requireNonNull(name, "name must not be null");
        }
    }

    /**
     * Formats a single row as a CSV line.
     *
     * BUG: String.format without an explicit Locale uses the JVM's default
     * locale. In locales that use a comma as the decimal separator (e.g.
     * German, French), "%.2f" produces "1,50" instead of "1.50", which
     * corrupts the CSV because comma is also the field delimiter.
     *
     * @param name  the name field
     * @param score the score field
     * @return a CSV-formatted line
     */
    public String formatRow(String name, double score) {
        return name + "," + String.format("%.2f", score);
        // BUG: locale-dependent decimal format
    }

    /**
     * Formats a DataRow as a CSV line.
     *
     * @param row the data row
     * @return a CSV-formatted line
     */
    public String formatRow(DataRow row) {
        return formatRow(row.name(), row.score());
    }

    /**
     * Exports a full CSV document with a header and all rows.
     *
     * @param rows the data rows to export
     * @return the complete CSV string
     */
    public String exportCsv(List<DataRow> rows) {
        String body = rows.stream()
                .map(this::formatRow)
                .collect(Collectors.joining("\n"));
        return HEADER + "\n" + body;
    }

    /**
     * Parses a CSV value back to a double.
     * Expects the dot as decimal separator.
     *
     * @param csvValue the string value from a CSV cell
     * @return the parsed double
     */
    public double parseScore(String csvValue) {
        return Double.parseDouble(csvValue);
    }

    /**
     * Exports and immediately parses back the scores to verify
     * round-trip consistency.
     *
     * @param rows the data rows
     * @return the parsed-back scores
     */
    public List<Double> roundTripScores(List<DataRow> rows) {
        return rows.stream()
                .map(this::formatRow)
                .map(line -> line.substring(line.lastIndexOf(',') + 1))
                .map(this::parseScore)
                .toList();
    }
}
