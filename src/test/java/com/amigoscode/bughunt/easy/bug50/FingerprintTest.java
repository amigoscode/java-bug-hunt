package com.amigoscode.bughunt.easy.bug50;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FingerprintTest {

    @Test
    void matchesIdenticalBytes() {
        Fingerprint a = new Fingerprint("SHA-256", new byte[]{1, 2, 3, 4});
        Fingerprint b = new Fingerprint("SHA-256", new byte[]{1, 2, 3, 4});

        assertThat(a.matches(b)).isTrue();
    }

    @Test
    void doesNotMatchDifferentBytes() {
        Fingerprint a = new Fingerprint("SHA-256", new byte[]{1, 2, 3, 4});
        Fingerprint b = new Fingerprint("SHA-256", new byte[]{9, 9, 9, 9});

        assertThat(a.matches(b)).isFalse();
    }

    @Test
    void doesNotMatchDifferentAlgorithm() {
        Fingerprint a = new Fingerprint("SHA-256", new byte[]{1, 2, 3, 4});
        Fingerprint b = new Fingerprint("MD5", new byte[]{1, 2, 3, 4});

        assertThat(a.matches(b)).isFalse();
    }
}
