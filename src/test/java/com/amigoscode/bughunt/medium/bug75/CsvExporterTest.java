package com.amigoscode.bughunt.medium.bug75;

import com.amigoscode.bughunt.medium.bug75.CsvExporter.DataRow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class CsvExporterTest {

    private final CsvExporter exporter = new CsvExporter();
    private Locale originalLocale;

    @BeforeEach
    void setGermanLocale() {
        originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.GERMAN);
    }

    @AfterEach
    void restoreLocale() {
        Locale.setDefault(originalLocale);
    }

    @Test
    void formatRowShouldUseDotAsDecimalSeparator() {
        String row = exporter.formatRow("Alice", 1.5);

        // CSV must always use dot as decimal separator regardless of locale
        assertThat(row).isEqualTo("Alice,1.50");
    }

    @Test
    void formatRowShouldNotProduceCommaInDecimal() {
        String row = exporter.formatRow("Bob", 99.99);

        // The score field must not contain a comma (it would break CSV parsing)
        String scoreField = row.substring(row.lastIndexOf(',') + 1);
        assertThat(scoreField).doesNotContain(",");
        assertThat(scoreField).isEqualTo("99.99");
    }

    @Test
    void exportCsvShouldProduceParsableOutput() {
        List<DataRow> rows = List.of(
                new DataRow("Alice", 85.5),
                new DataRow("Bob", 92.75)
        );

        String csv = exporter.exportCsv(rows);
        String[] lines = csv.split("\n");

        assertThat(lines[0]).isEqualTo("name,score");
        assertThat(lines[1]).isEqualTo("Alice,85.50");
        assertThat(lines[2]).isEqualTo("Bob,92.75");
    }

    @Test
    void roundTripScoresShouldPreserveValues() {
        List<DataRow> rows = List.of(
                new DataRow("Alice", 1.5),
                new DataRow("Bob", 3.14)
        );

        // Format to CSV and parse back -- values should survive the round trip
        List<Double> parsed = exporter.roundTripScores(rows);

        assertThat(parsed).containsExactly(1.5, 3.14);
    }

    @Test
    void formatRowShouldHandleWholeNumbers() {
        String row = exporter.formatRow("Carol", 100.0);

        assertThat(row).isEqualTo("Carol,100.00");
    }
}
