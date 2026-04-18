# Bug 90 -- Double.NaN comparison is always false (or true for !=)

**Bug:** `isValid(double reading)` uses `reading != Double.NaN` to check for NaN. By the IEEE 754 standard, NaN is not equal to anything -- including itself. So `NaN != NaN` evaluates to `true`, and every NaN reading passes the filter.

```java
// Buggy:
public boolean isValid(double reading) {
    return reading != Double.NaN       // always true -- useless check
            && !(reading < 0)          // NaN < 0 is false, so !(false) = true
            && !(reading > maxReading); // NaN > max is false, so !(false) = true
}
```

The negated comparisons `!(reading < 0)` and `!(reading > maxReading)` both evaluate to `true` for NaN because all comparisons with NaN return `false`. Combined with the useless `reading != Double.NaN` check (which is always `true`), NaN passes all three conditions.

**Fix:** Use `Double.isNaN()` and positive comparisons:

```java
// Fixed:
public boolean isValid(double reading) {
    return !Double.isNaN(reading) && reading >= 0 && reading <= maxReading;
}
```

**Lesson:** Never compare a value to `Double.NaN` or `Float.NaN` using `==` or `!=`. These comparisons always return `false` (for `==`) or `true` (for `!=`) regardless of the operand. Always use `Double.isNaN(value)` or `Float.isNaN(value)` instead.
