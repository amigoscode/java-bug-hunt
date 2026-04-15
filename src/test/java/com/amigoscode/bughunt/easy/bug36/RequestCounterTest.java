package com.amigoscode.bughunt.easy.bug36;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestCounterTest {

    @Test
    void twoEndpointsCountIndependently() {
        RequestCounter api = new RequestCounter("/api");
        RequestCounter admin = new RequestCounter("/admin");

        api.record();
        api.record();
        api.record();

        admin.record();

        assertThat(api.current()).isEqualTo(3);
        assertThat(admin.current()).isEqualTo(1);
    }

    @Test
    void recordBatchOnSingleCounter() {
        RequestCounter c = new RequestCounter("/single");
        c.recordBatch(5);

        assertThat(c.current()).isEqualTo(5);
    }
}
