# Bug 16 — Eager `&` instead of short-circuit `&&`

**Bug:** `&` evaluates *all* operands regardless of previous results. When `divisor` is `null`, the expression `divisor != 0` unboxes `null` → `NullPointerException`.

**Fix:** Use `&&` — it short-circuits:
```java
return numerator != null && divisor != null && divisor != 0;
```

**Lesson:** `&&` and `||` short-circuit (stop as soon as the result is known). `&` and `|` are *bitwise / eager boolean* — they always evaluate everything. Use `&&`/`||` for null-guards and any boolean chain.
