# Bug 56 — `.toString()` on null throws NPE

**Bug:** `formatEntry` calls `entry.getMessage().toString()` and `entry.getSource().toString()`. When `message` or `source` is `null`, this throws a `NullPointerException`. The `.toString()` call is redundant on a `String` but fatal on `null`.

**Fix:** Use `String.valueOf()` or `Objects.toString()` which handle null safely:
```java
public String formatEntry(LogEntry entry) {
    String ts = entry.getTimestamp().format(FORMATTER);
    String lvl = entry.getLevel().name();
    String src = String.valueOf(entry.getSource());
    String msg = String.valueOf(entry.getMessage());
    return String.format("[%s] %s %s — %s", ts, lvl, src, msg);
}
```

`String.valueOf(null)` returns the string `"null"`, while `null.toString()` throws `NullPointerException`.

**Lesson:** Never call `.toString()` on a reference that might be null. Use `String.valueOf()`, `Objects.toString(obj, "default")`, or a null check. This is especially common when fields are optional or come from external input where nulls are possible.
