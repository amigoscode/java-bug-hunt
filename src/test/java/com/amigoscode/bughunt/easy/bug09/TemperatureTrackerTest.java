package com.amigoscode.bughunt.easy.bug09;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemperatureTrackerTest {

    @Test
    void tracksPositiveReadings() {
        TemperatureTracker t = new TemperatureTracker();
        t.record(25);
        t.record(30);
        t.record(18);

        assertThat(t.min()).isEqualTo(18);
        assertThat(t.max()).isEqualTo(30);
    }

    @Test
    void tracksNegativeReadings() {
        TemperatureTracker t = new TemperatureTracker();
        t.record(-5);
        t.record(-12);

        assertThat(t.min()).isEqualTo(-12);
        assertThat(t.max()).isEqualTo(-5);
    }
}
