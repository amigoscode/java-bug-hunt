# Bug 27 — `Optional.get()` without emptiness check

**Bug:** `.get()` on an empty `Optional` throws `NoSuchElementException`. When the user doesn't exist, `displayName` blows up instead of returning a sensible default.

**Fix:** Use `orElse` (like `emailDomain` already does):
```java
return findById(id)
        .map(User::name)
        .map(String::toUpperCase)
        .orElse("UNKNOWN");
```

**Lesson:** `Optional.get()` is almost always a code smell. Prefer `orElse`, `orElseGet`, `orElseThrow(() -> new ...)`, or `ifPresent`. If you really can prove the Optional is non-empty (rare), use `.orElseThrow()` — it's more intention-revealing and throws the same exception.
