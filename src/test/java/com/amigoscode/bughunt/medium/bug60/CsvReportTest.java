package com.amigoscode.bughunt.medium.bug60;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvReportTest {

    private Iterator<String> sampleCsv() {
        return List.of(
                "name,age,city",
                "Alice,30,London",
                "Bob,25,Berlin",
                "Charlie,35,Paris"
        ).iterator();
    }

    @Test
    void rowCountThenHeadersShouldBothWork() {
        CsvReport report = new CsvReport(sampleCsv());

        int count = report.rowCount();
        List<String> headers = report.headers();

        assertThat(count).isEqualTo(4);
        assertThat(headers).containsExactly("name", "age", "city");
    }

    @Test
    void summaryShouldReportCorrectDimensions() {
        CsvReport report = new CsvReport(sampleCsv());

        String summary = report.summary();

        assertThat(summary).isEqualTo("4 rows, 3 columns");
    }

    @Test
    void headersThenDataRowsShouldBothWork() {
        CsvReport report = new CsvReport(sampleCsv());

        List<String> headers = report.headers();
        List<List<String>> data = report.dataRows();

        assertThat(headers).containsExactly("name", "age", "city");
        assertThat(data).hasSize(3);
        assertThat(data.get(0)).containsExactly("Alice", "30", "London");
    }
}
