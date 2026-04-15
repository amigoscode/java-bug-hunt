# Bug 42 — Binary search wrong loop bound

**Bug:** `while (low < high)` exits when `low == high`, but when the bounds converge on the target, the value at that index is never checked. A singleton array or searching for the last/first element can miss.

**Fix:** Use `<=`:
```java
while (low <= high) { ... }
```

**Lesson:** With *inclusive* bounds (`high = length - 1`), the condition must be `<=`. With *exclusive* bounds (`high = length`), use `<`. Pick one convention and stick to it. When in doubt, use `java.util.Arrays.binarySearch`.
