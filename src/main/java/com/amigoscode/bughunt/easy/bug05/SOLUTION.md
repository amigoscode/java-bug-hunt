# Bug 05 — Integer overflow

**Bug:** `result` is `int`. `factorial(20)` overflows silently. The signature already returns `long` — the accumulator is the problem.

**Fix:** `long result = 1L;`

**Lesson:** Java integer arithmetic wraps silently on overflow. Use `long` for factorials up to 20, `BigInteger` beyond. Consider `Math.multiplyExact` for overflow detection.
