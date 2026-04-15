# Bug 04 — Integer division

**Bug:** `part / total` is int division. `1 / 2 = 0`, then `0 * 100 = 0`.

**Fix:** `return (part * 100.0) / total;` or cast: `return ((double) part / total) * 100;`

**Lesson:** When at least one operand is `double`, Java uses floating-point division. Integer / integer always truncates toward zero.
