# Bug 01 — Null Pointer Exception

**Bug:** `greet(null)` throws NPE on `name.toUpperCase()`.

**Fix:**
```java
String safe = (name == null) ? "guest" : name;
return "Hello, " + safe.toUpperCase() + "!";
```

**Lesson:** Always guard against null inputs at public API boundaries, or use `Objects.requireNonNullElse(name, "guest")`.
