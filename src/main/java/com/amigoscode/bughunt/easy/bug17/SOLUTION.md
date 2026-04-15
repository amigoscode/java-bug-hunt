# Bug 17 — Cast binding precedence

**Bug:** `(int) dollars * CENTS_PER_DOLLAR` casts `dollars` to `int` *first* (truncating the decimal), then multiplies by 100. `1.99` → `1` → `100`, not `199`.

**Fix:** Parenthesise the multiplication, or round properly:
```java
return (int) Math.round(dollars * CENTS_PER_DOLLAR);
```
`Math.round` is preferable — plain cast truncates, so `1.999 * 100 = 199.9` → `199` (losing a cent).

**Lesson:** Casts in Java bind tighter than arithmetic. And for money, prefer `BigDecimal` or integer cents throughout — `double` loses precision.
