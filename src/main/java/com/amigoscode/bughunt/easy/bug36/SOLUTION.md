# Bug 36 — Accidental `static` field

**Bug:** `count` is `static` — it belongs to the *class*, not the instance. Every `RequestCounter` shares the same counter. Incrementing `api` also shows up in `admin.current()`.

**Fix:** Make it an instance field:
```java
private int count = 0;
```

**Lesson:** `static` = per-class. For per-instance state, omit `static`. Static fields are a common source of leaks between tests, between tenants, between requests — they're global mutable state, which is almost always wrong. When you truly need shared state, make the sharing explicit (and thread-safe).
