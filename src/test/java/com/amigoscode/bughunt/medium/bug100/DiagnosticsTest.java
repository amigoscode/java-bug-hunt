package com.amigoscode.bughunt.medium.bug100;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiagnosticsTest {

    @Test
    void formatItemsShouldProduceReadableOutput() {
        Diagnostics diag = new Diagnostics("api-gateway");

        String result = diag.formatItems(new String[]{"cpu", "memory", "disk"});

        // BUG: %s on a String[] calls Object.toString(), producing
        // something like "Items: [Ljava.lang.String;@1a2b3c4d"
        assertThat(result).isEqualTo("Items: [cpu, memory, disk]");
    }

    @Test
    void formatItemsShouldHandleSingleItem() {
        Diagnostics diag = new Diagnostics("db-primary");

        String result = diag.formatItems(new String[]{"uptime"});

        assertThat(result).isEqualTo("Items: [uptime]");
    }

    @Test
    void formatMetricsShouldShowNamesAndValues() {
        Diagnostics diag = new Diagnostics("worker-pool");

        String result = diag.formatMetrics(
                new String[]{"latency", "throughput"},
                new int[]{42, 1000}
        );

        assertThat(result).isEqualTo("[worker-pool] Metrics: [latency, throughput] = [42, 1000]");
    }

    @Test
    void activeServicesShouldListServicesReadably() {
        Diagnostics diag = new Diagnostics("cluster");

        String result = diag.activeServices(new String[]{"auth", "billing", "search"});

        assertThat(result).isEqualTo("Active services (3): [auth, billing, search]");
    }

    @Test
    void reportCountShouldIncrement() {
        Diagnostics diag = new Diagnostics("monitor");

        diag.formatItems(new String[]{"a"});
        diag.activeServices(new String[]{"b"});

        assertThat(diag.getReportCount()).isEqualTo(2);
    }
}
