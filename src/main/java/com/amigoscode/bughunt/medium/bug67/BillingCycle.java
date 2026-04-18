package com.amigoscode.bughunt.medium.bug67;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manages recurring billing cycles for subscription services.
 * Calculates future billing dates based on a start date and monthly intervals.
 */
public class BillingCycle {

    private final LocalDate startDate;
    private final String planName;
    private final int dayOfMonth;

    /**
     * Creates a billing cycle starting on the given date.
     * The billing day is derived from the start date's day-of-month.
     *
     * @param startDate the first billing date
     * @param planName  the subscription plan name
     */
    public BillingCycle(LocalDate startDate, String planName) {
        this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
        this.planName = Objects.requireNonNull(planName, "planName must not be null");
        this.dayOfMonth = startDate.getDayOfMonth();
    }

    /**
     * Returns the original start date.
     */
    public LocalDate startDate() {
        return startDate;
    }

    /**
     * Returns the intended day of month for billing.
     */
    public int billingDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Returns the plan name.
     */
    public String planName() {
        return planName;
    }

    /**
     * Calculates the billing date for the Nth billing cycle (1-indexed).
     * Cycle 1 is the start date, cycle 2 is one month later, etc.
     *
     * BUG: Uses iterative plusMonths(1) from the previous date, which causes
     * end-of-month truncation to drift. Jan 31 -> Feb 28 -> Mar 28 (not Mar 31).
     *
     * @param cycleNumber the cycle number (1-indexed)
     * @return the billing date for that cycle
     */
    public LocalDate billingDateForCycle(int cycleNumber) {
        if (cycleNumber < 1) {
            throw new IllegalArgumentException("Cycle number must be >= 1");
        }

        // BUG: iterating month-by-month causes drift at end-of-month boundaries
        LocalDate date = startDate;
        for (int i = 1; i < cycleNumber; i++) {
            date = date.plusMonths(1);
        }
        return date;
    }

    /**
     * Returns a list of all billing dates for the given number of cycles.
     *
     * @param numberOfCycles how many cycles to generate
     * @return list of billing dates
     */
    public List<LocalDate> allBillingDates(int numberOfCycles) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 1; i <= numberOfCycles; i++) {
            dates.add(billingDateForCycle(i));
        }
        return dates;
    }

    /**
     * Checks whether a given date is a billing date within the first N cycles.
     *
     * @param date           the date to check
     * @param maxCyclesToCheck how many cycles to check
     * @return true if the date matches a billing date
     */
    public boolean isBillingDate(LocalDate date, int maxCyclesToCheck) {
        for (int i = 1; i <= maxCyclesToCheck; i++) {
            if (billingDateForCycle(i).equals(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a summary string for the billing cycle.
     */
    public String summary() {
        return String.format("BillingCycle[plan=%s, start=%s, billingDay=%d]",
                planName, startDate, dayOfMonth);
    }
}
