# Bug 59 — Duplicate key in Map.of() throws IllegalArgumentException

**Bug:** The constructor passes `"db.host"` twice to `Map.of()`. Unlike `HashMap.put()` which silently overwrites, `Map.of()` throws `IllegalArgumentException` on duplicate keys. The config object can never be constructed.

**Fix:** Remove the duplicate and keep only the intended value:
```java
this.config = Map.of(
        "db.host", environment + ".db.example.com",
        "db.port", "5432",
        "db.name", environment + "_db",
        "db.pool.size", "10",
        "db.timeout.ms", "5000",
        "app.name", "my-service",
        "app.env", environment
);
```

Or, if you need a default that gets overridden, use a mutable map:
```java
Map<String, String> m = new HashMap<>();
m.put("db.host", "localhost");           // default
m.put("db.host", environment + ".db.example.com"); // override
this.config = Map.copyOf(m);
```

**Lesson:** `Map.of()` and `Map.ofEntries()` are strict — they reject `null` keys, `null` values, and duplicate keys. This is by design: an immutable map with duplicate keys would be ambiguous. When building config maps where keys may repeat (e.g. defaults + overrides), use a `HashMap` first, then wrap with `Map.copyOf()`.
