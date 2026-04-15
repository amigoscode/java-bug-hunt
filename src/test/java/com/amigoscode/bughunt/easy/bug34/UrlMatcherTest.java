package com.amigoscode.bughunt.easy.bug34;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlMatcherTest {

    private final UrlMatcher matcher = new UrlMatcher();

    @Test
    void acceptsFullHttpsUrl() {
        assertThat(matcher.isValidUrl("https://example.com/path")).isTrue();
    }

    @Test
    void acceptsFullHttpUrl() {
        assertThat(matcher.isValidUrl("http://example.com")).isTrue();
    }

    @Test
    void rejectsPathWithoutProtocol() {
        assertThat(matcher.isValidUrl("example.com")).isFalse();
    }
}
