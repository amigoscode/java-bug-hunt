# Bug 33 — `new BigDecimal(double)` loses precision

**Bug:** `new BigDecimal(0.1)` stores the *actual double representation*, which is `0.1000000000000000055511151231257827021181583404541015625`. Arithmetic accumulates garbage digits, and the first test passes only because `setScale(2, HALF_UP)` rounds the mess away.

**Fix:** Use the String constructor, or `BigDecimal.valueOf(double)` (which routes through `Double.toString`):
```java
BigDecimal unit = BigDecimal.valueOf(l.unitPrice());
```

**Lesson:** `new BigDecimal(double)` is almost always a mistake — it's exact-to-the-float, not exact-to-the-number-you-typed. For user input, pass the String. For converting a computed `double`, `BigDecimal.valueOf` is safest. For money calculations, avoid `double` end-to-end — use `BigDecimal` or `long` cents from the start.
