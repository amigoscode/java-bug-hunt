package com.amigoscode.bughunt.easy.bug30;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportBuilderTest {

    @Test
    void buildsReportWithCountAndJoinedEntries() {
        ReportBuilder rb = new ReportBuilder("sales");

        String report = rb.build(List.of("europe", "asia", "africa"));

        assertThat(report).isEqualTo("sales [3]: europe, asia, africa");
    }

    @Test
    void filtersBlankAndNull() {
        ReportBuilder rb = new ReportBuilder("sales");

        String report = rb.build(java.util.Arrays.asList("europe", "", null, "asia"));

        assertThat(report).isEqualTo("sales [2]: europe, asia");
    }
}
