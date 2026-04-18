# Bug 83 -- Record compact constructor with wrong upper bound

**Bug:** The compact constructor of the `Percentage` record validates that the value is between 0 and 100, but the upper-bound check uses `>=` instead of `>`. This means `new Percentage(100)` throws an `IllegalArgumentException`, even though 100% is a perfectly valid percentage.

```java
// Buggy:
public Percentage {
    if (value < 0 || value >= 100) {  // rejects 100!
        throw new IllegalArgumentException(...);
    }
}
```

**Fix:** Change the upper-bound operator to `>`:

```java
// Fixed:
public Percentage {
    if (value < 0 || value > 100) {  // now 100 is accepted
        throw new IllegalArgumentException(...);
    }
}
```

**Lesson:** Off-by-one errors in range validation are extremely common, especially with inclusive vs exclusive bounds. When documenting a range as "0 to 100 inclusive", make sure the code actually uses `<=` / `>=` consistently. Record compact constructors are the canonical place for validation in Java records -- getting the bounds wrong here means every construction site is affected and there is no workaround.
