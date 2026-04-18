# Bug 88 -- Math.abs(Integer.MIN_VALUE) is negative

**Bug:** `distance(int a, int b)` computes `Math.abs(a - b)`. When `a - b` overflows the 32-bit `int` range, the result wraps around to a negative number. Specifically, `Math.abs(Integer.MIN_VALUE)` returns `Integer.MIN_VALUE` because the positive counterpart (`2147483648`) cannot be represented as an `int`.

```java
// Buggy:
public long distance(int a, int b) {
    return Math.abs(a - b);  // overflow when a - b < Integer.MIN_VALUE or > Integer.MAX_VALUE
}
```

For example, `Integer.MAX_VALUE - (-1)` = `2147483647 - (-1)` = `2147483648`, which overflows to `-2147483648`. Then `Math.abs(-2147483648)` returns `-2147483648`.

**Fix:** Cast to `long` before subtracting to avoid overflow:

```java
// Fixed:
public long distance(int a, int b) {
    return Math.abs((long) a - (long) b);
}
```

**Lesson:** Integer arithmetic in Java silently overflows. When computing differences that might exceed the `int` range, widen to `long` first. Also remember that `Math.abs(Integer.MIN_VALUE)` and `Math.abs(Long.MIN_VALUE)` both return the same negative input value -- there is no positive representation in the same type.
