package com.amigoscode.bughunt.easy.bug20;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailDomainExtractorTest {

    private final EmailDomainExtractor extractor = new EmailDomainExtractor();

    @Test
    void extractsDomainFromValidEmail() {
        assertThat(extractor.domain("ada@example.com")).isEqualTo("example.com");
    }

    @Test
    void extractsLocalPart() {
        assertThat(extractor.localPart("ada@example.com")).isEqualTo("ada");
    }

    @Test
    void throwsIllegalArgumentWhenNoAtSign() {
        assertThatThrownBy(() -> extractor.domain("not-an-email"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("@");
    }
}
