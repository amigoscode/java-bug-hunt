# Bug 18 — Integer division before rounding

**Bug:** `totalScore / ratings.size()` is `int / int`, truncating before `Math.round` ever sees a fractional value. `9 / 2 = 4` (not `4.5`), so `Math.round(4)` = `4`, never `5`.

**Fix:** Force floating-point division *before* rounding:
```java
return (int) Math.round((double) totalScore / ratings.size());
```

**Lesson:** Rounding is a no-op if you've already truncated. Cast one operand to `double` to get real division.
