package com.amigoscode.bughunt.easy.bug30;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportBuilder {

    private final String reportName;

    public ReportBuilder(String reportName) {
        this.reportName = reportName;
    }

    public String build(List<String> entries) {
        Stream<String> pipeline = entries.stream()
                .filter(e -> e != null)
                .filter(e -> !e.isBlank())
                .map(String::trim);

        long count = pipeline.count();
        String joined = pipeline.collect(Collectors.joining(", "));

        return reportName + " [" + count + "]: " + joined;
    }

    public String name() {
        return reportName;
    }
}
