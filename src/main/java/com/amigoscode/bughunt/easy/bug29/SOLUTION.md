# Bug 29 — `return` in `finally` overrides everything

**Bug:** `finally` runs after the `try` block's `return`. A `return` *inside* `finally` silently replaces the value (and even swallows thrown exceptions). Every call to `attemptWithdrawal` returns `99`.

**Fix:** Remove the `return` from `finally`. Use `finally` only for cleanup (closing resources, releasing locks) — never control flow:
```java
try {
    // ...
    return 0;
} finally {
    // cleanup only, no return/throw
}
```

**Lesson:** Never `return` or `throw` from `finally`. It masks bugs *and* discards exceptions from the `try` block. If you need cleanup with a value, use try-with-resources or extract the logic.
