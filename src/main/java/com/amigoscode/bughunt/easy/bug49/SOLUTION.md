# Bug 49 — Wrong first argument in Euclidean recursion

**Bug:** The correct recursion is `gcd(b, a % b)`. The code has `gcd(a, a % b)` — the first argument should advance to `b`, not stay at `a`. Trace `gcd(12, 8)`:

- call: `gcd(12, 8)` → `gcd(12, 12 % 8 = 4)`
- call: `gcd(12, 4)` → `gcd(12, 12 % 4 = 0)`
- base case: returns `12`. Wrong — should be `4`.

**Fix:**
```java
return gcd(b, a % b);
```

**Lesson:** The Euclidean algorithm shrinks `b` each step; forgetting to shift `a` to the previous `b` keeps `a` constant and the base case returns it unchanged. When translating a math formula into code, write the formula beside the call and match each argument position.
