package com.amigoscode.bughunt.medium.bug66;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailNotificationTest {

    @Test
    void logEntryShouldContainRecipient() {
        EmailNotification notification =
                new EmailNotification("Hello", "ada@test.com", "Greeting");

        // The logEntry is captured during construction via toString().
        // It should contain the recipient address.
        assertThat(notification.logEntry()).contains("ada@test.com");
    }

    @Test
    void logEntryShouldContainMessage() {
        EmailNotification notification =
                new EmailNotification("System alert", "bob@example.org");

        assertThat(notification.logEntry()).contains("System alert");
    }

    @Test
    void logEntryShouldNotContainNullLiteral() {
        EmailNotification notification =
                new EmailNotification("Welcome", "carol@acme.com", "Welcome aboard");

        // The log entry should never contain the literal string "null"
        assertThat(notification.logEntry()).doesNotContain("null");
    }

    @Test
    void toStringAfterConstructionShouldBeCorrect() {
        EmailNotification notification =
                new EmailNotification("Test", "dave@test.com");

        // Calling toString() after construction works fine
        assertThat(notification.toString()).contains("dave@test.com");
    }

    @Test
    void auditSummaryShouldIncludeRecipient() {
        EmailNotification notification =
                new EmailNotification("Urgent", "eve@company.io", "Action Required");

        // auditSummary delegates to logEntry, which should have the recipient
        assertThat(notification.auditSummary()).contains("eve@company.io");
    }
}
