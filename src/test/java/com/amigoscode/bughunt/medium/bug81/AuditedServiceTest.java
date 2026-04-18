package com.amigoscode.bughunt.medium.bug81;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuditedServiceTest {

    @Test
    void tagShouldContainLogPrefix() {
        AuditedService service = new AuditedService("OrderService");

        String tag = service.tag();

        assertThat(tag).contains("LOG:OrderService");
    }

    @Test
    void tagShouldContainAuditPrefix() {
        AuditedService service = new AuditedService("OrderService");

        String tag = service.tag();

        // BUG: tag only contains LOG:OrderService -- AUDIT is missing
        assertThat(tag).contains("AUDIT:OrderService");
    }

    @Test
    void tagShouldCombineBothPrefixes() {
        AuditedService service = new AuditedService("PaymentService");

        String tag = service.tag();

        assertThat(tag)
                .contains("LOG:PaymentService")
                .contains("AUDIT:PaymentService");
    }

    @Test
    void logLineShouldIncludeBothTags() {
        AuditedService service = new AuditedService("UserService");

        String line = service.logLine("user created");

        assertThat(line).contains("AUDIT:UserService");
        assertThat(line).contains("user created");
    }

    @Test
    void auditEntryShouldIncludeBothTags() {
        AuditedService service = new AuditedService("UserService");

        String entry = service.auditEntry("warn", "suspicious login");

        assertThat(entry).contains("AUDIT:UserService");
        assertThat(entry).contains("[WARN]");
        assertThat(entry).contains("suspicious login");
    }
}
