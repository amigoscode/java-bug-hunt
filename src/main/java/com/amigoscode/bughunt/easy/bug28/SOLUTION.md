# Bug 28 — Lost exception message and cause

**Bug:** `throw new RuntimeException()` discards both the message and the original `IOException`. Debugging a failure requires reading a stack trace that has *no idea what file failed or why*.

**Fix:** Preserve message + cause:
```java
throw new RuntimeException("Failed to load " + target, e);
```

**Lesson:** When rethrowing, always pass the original exception as the `cause` (second constructor arg). Include context the user will need to diagnose the problem — file paths, identifiers, etc. Consider using a domain exception type (`ConfigLoadException`) instead of raw `RuntimeException`.
