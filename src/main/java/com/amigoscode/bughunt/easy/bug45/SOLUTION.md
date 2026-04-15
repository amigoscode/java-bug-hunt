# Bug 45 — Fast exponentiation forgets the odd case

**Bug:** When `exponent` is odd, integer division loses a factor. `power(2, 3)` → `power(2, 1) * power(2, 1) = 2 * 2 = 4` — missing one multiplication by `base`.

**Fix:** Handle odd exponents separately:
```java
if (exponent == 0) return 1L;
long half = power(base, exponent / 2);
long squared = half * half;
return (exponent % 2 == 0) ? squared : squared * base;
```

**Lesson:** Fast exponentiation (exponentiation by squaring) works because `x^n = (x^(n/2))^2` when `n` is even, and `x * (x^(n/2))^2` when `n` is odd. Skipping the odd branch silently produces wrong answers for half the inputs. Always test boundary cases: 0, 1, even, odd.
