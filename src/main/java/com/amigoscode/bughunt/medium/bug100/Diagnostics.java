package com.amigoscode.bughunt.medium.bug100;

import java.util.Objects;

/**
 * Provides diagnostic formatting utilities for system monitoring.
 * Generates human-readable reports from arrays of data.
 *
 * Used by the health-check endpoint to produce status messages.
 */
public class Diagnostics {

    private final String systemName;
    private int reportCount;

    /**
     * Creates a diagnostics instance for the given system.
     *
     * @param systemName the name of the system being diagnosed
     */
    public Diagnostics(String systemName) {
        this.systemName = Objects.requireNonNull(systemName, "systemName must not be null");
        this.reportCount = 0;
    }

    /**
     * Formats an array of items into a readable string.
     *
     * BUG: Uses {@code String.format("%s", items)} where {@code items}
     * is a {@code String[]}. The {@code %s} format specifier calls
     * {@code toString()} on the array, which produces the default
     * array representation like {@code [Ljava.lang.String;@1a2b3c4d}
     * instead of a readable list.
     *
     * @param items the items to format
     * @return a formatted string listing the items
     */
    public String formatItems(String[] items) {
        Objects.requireNonNull(items, "items must not be null");
        reportCount++;
        // BUG: %s on an array calls Object.toString(), producing garbage
        return String.format("Items: %s", (Object) items);
    }

    /**
     * Formats system metrics into a diagnostic report.
     *
     * BUG: Same issue -- {@code %s} on {@code int[]} produces
     * {@code [I@hash} instead of readable numbers.
     *
     * @param metricNames the names of the metrics
     * @param values      the metric values
     * @return a formatted report
     */
    public String formatMetrics(String[] metricNames, int[] values) {
        Objects.requireNonNull(metricNames, "metricNames must not be null");
        Objects.requireNonNull(values, "values must not be null");
        reportCount++;
        // BUG: %s on arrays produces garbage output
        return String.format("[%s] Metrics: %s = %s", systemName, (Object) metricNames, (Object) values);
    }

    /**
     * Generates a summary line for a set of active services.
     *
     * BUG: Same array-toString issue.
     *
     * @param services the active service names
     * @return a summary string
     */
    public String activeServices(String[] services) {
        Objects.requireNonNull(services, "services must not be null");
        reportCount++;
        // BUG: %s on String[] calls Object.toString()
        return String.format("Active services (%d): %s", services.length, (Object) services);
    }

    /**
     * Returns the number of reports generated so far.
     *
     * @return the report count
     */
    public int getReportCount() {
        return reportCount;
    }

    /**
     * Returns the system name.
     *
     * @return the system name
     */
    public String getSystemName() {
        return systemName;
    }

    @Override
    public String toString() {
        return "Diagnostics{system='" + systemName
                + "', reports=" + reportCount + "}";
    }
}
