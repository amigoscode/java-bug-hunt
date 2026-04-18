# Bug 67 -- LocalDate.plusMonths end-of-month drift

**Bug:** The `billingDateForCycle` method iteratively adds one month at a time from the previous result. When the start date is an end-of-month date like January 31, `plusMonths(1)` yields February 28 (truncated to fit February). Then `plusMonths(1)` from February 28 yields March 28 -- not March 31. The billing day drifts permanently to the 28th.

```java
// Buggy: iterative month addition causes drift
LocalDate date = startDate; // Jan 31
for (int i = 1; i < cycleNumber; i++) {
    date = date.plusMonths(1); // Feb 28, then Mar 28, Apr 28, ...
}
```

**Fix:** Always compute from the original start date to avoid cumulative truncation:

```java
public LocalDate billingDateForCycle(int cycleNumber) {
    return startDate.plusMonths(cycleNumber - 1);
}
```

This way, `Jan 31 + 2 months = Mar 31` directly, without the intermediate February truncation.

**Lesson:** `LocalDate.plusMonths` adjusts the day-of-month to fit the target month's length. When you chain these calls iteratively, the truncation from a short month (like February) persists into subsequent months. Always calculate relative to the original anchor date to preserve the intended day-of-month.
