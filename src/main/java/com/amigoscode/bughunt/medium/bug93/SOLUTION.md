# Bug 93 -- Comparator subtraction overflow

**Bug:** The `sortedByPrice` method sorts products using a comparator that subtracts prices: `(a, b) -> a.priceCents() - b.priceCents()`. When one price is near `Integer.MAX_VALUE` and another near `Integer.MIN_VALUE`, the subtraction overflows. For example, `Integer.MAX_VALUE - Integer.MIN_VALUE` wraps to `-1`, making the comparator claim the larger value is smaller.

```java
// Buggy:
sorted.sort((a, b) -> a.priceCents() - b.priceCents());

// Example overflow:
// MAX_VALUE(2147483647) - MIN_VALUE(-2147483648) = -1 (overflow!)
// The comparator says MAX_VALUE < MIN_VALUE
```

**Fix:** Use `Integer.compare()` which handles all ranges safely:

```java
// Fixed:
sorted.sort((a, b) -> Integer.compare(a.priceCents(), b.priceCents()));

// Or use Comparator factory:
sorted.sort(Comparator.comparingInt(Product::priceCents));
```

**Lesson:** Never use subtraction as a comparator for integers. The "subtract trick" `(a - b)` is an old idiom that silently overflows when values span a wide range. Always use `Integer.compare()`, `Long.compare()`, or `Comparator.comparingInt()`. The subtraction trick only works safely when you can guarantee the difference fits in an `int` (e.g., both values are non-negative and small).
