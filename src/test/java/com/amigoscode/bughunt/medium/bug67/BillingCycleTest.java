package com.amigoscode.bughunt.medium.bug67;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BillingCycleTest {

    @Test
    void billingDayShouldBePreservedAcrossMonths() {
        // Start billing on January 31
        BillingCycle cycle = new BillingCycle(
                LocalDate.of(2025, 1, 31), "Premium");

        // Cycle 3 should be March 31 (preserving the 31st)
        // BUG: iterative plusMonths gives Jan 31 -> Feb 28 -> Mar 28 (drift!)
        assertThat(cycle.billingDateForCycle(3))
                .isEqualTo(LocalDate.of(2025, 3, 31));
    }

    @Test
    void billingDateForCycleOneShouldBeStartDate() {
        LocalDate start = LocalDate.of(2025, 5, 15);
        BillingCycle cycle = new BillingCycle(start, "Basic");

        assertThat(cycle.billingDateForCycle(1)).isEqualTo(start);
    }

    @Test
    void allBillingDatesShouldPreserveDayOfMonth() {
        BillingCycle cycle = new BillingCycle(
                LocalDate.of(2025, 1, 31), "Enterprise");

        List<LocalDate> dates = cycle.allBillingDates(4);

        // All billing dates should land on the 31st (or last day) of each month
        // Jan 31, Feb 28, Mar 31, Apr 30
        assertThat(dates.get(2).getDayOfMonth())
                .as("March billing should be on the 31st")
                .isEqualTo(31);
    }

    @Test
    void billingDayOfMonthShouldMatchStartDate() {
        BillingCycle cycle = new BillingCycle(
                LocalDate.of(2025, 1, 31), "Standard");

        assertThat(cycle.billingDayOfMonth()).isEqualTo(31);
    }

    @Test
    void marchBillingShouldBeRecognizedAsValidBillingDate() {
        BillingCycle cycle = new BillingCycle(
                LocalDate.of(2025, 1, 31), "Pro");

        // March 31 should be recognized as a billing date
        assertThat(cycle.isBillingDate(LocalDate.of(2025, 3, 31), 6))
                .isTrue();
    }
}
