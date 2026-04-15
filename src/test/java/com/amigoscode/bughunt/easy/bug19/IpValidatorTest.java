package com.amigoscode.bughunt.easy.bug19;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IpValidatorTest {

    private final IpValidator validator = new IpValidator();

    @Test
    void parsesFourOctets() {
        assertThat(validator.parse("192.168.1.1"))
                .containsExactly(192, 168, 1, 1);
    }

    @Test
    void acceptsValidAddress() {
        assertThat(validator.isValid("10.0.0.1")).isTrue();
    }

    @Test
    void rejectsOctetOutOfRange() {
        assertThat(validator.isValid("10.0.0.300")).isFalse();
    }

    @Test
    void normaliseReturnsCanonical() {
        assertThat(validator.normalise("192.168.1.1")).isEqualTo("192.168.1.1");
    }
}
