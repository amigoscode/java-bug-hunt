package com.amigoscode.bughunt.medium.bug56;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class LogFormatterTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2025, 6, 15, 10, 30, 0);

    @Test
    void formatEntryHandlesNullMessage() {
        LogFormatter formatter = new LogFormatter();
        LogFormatter.LogEntry entry = new LogFormatter.LogEntry(
                LogFormatter.Level.WARN, null, "AuthService", NOW
        );

        // Formatting a log entry with null message should not throw
        assertThatNoException().isThrownBy(() -> formatter.formatEntry(entry));
    }

    @Test
    void formatEntryWithNullMessageProducesReadableOutput() {
        LogFormatter formatter = new LogFormatter();
        LogFormatter.LogEntry entry = new LogFormatter.LogEntry(
                LogFormatter.Level.ERROR, null, "PaymentGateway", NOW
        );

        String result = formatter.formatEntry(entry);

        assertThat(result).contains("ERROR");
        assertThat(result).contains("PaymentGateway");
        assertThat(result).contains("null");
    }

    @Test
    void formatEntryHandlesNullSource() {
        LogFormatter formatter = new LogFormatter();
        LogFormatter.LogEntry entry = new LogFormatter.LogEntry(
                LogFormatter.Level.INFO, "System started", null, NOW
        );

        assertThatNoException().isThrownBy(() -> formatter.formatEntry(entry));
    }
}
