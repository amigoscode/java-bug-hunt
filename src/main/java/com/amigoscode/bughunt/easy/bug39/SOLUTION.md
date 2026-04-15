# Bug 39 — Unboxing `null` from `Map.get`

**Bug:** `flags.get("never-configured")` returns `null`. Returning that from `isEnabled` auto-unboxes `Boolean` → `boolean`, throwing `NullPointerException`.

**Fix:** Use `getOrDefault`, or null-check:
```java
public boolean isEnabled(String flag) {
    return flags.getOrDefault(flag, false);
}
```

**Lesson:** `Map<K, V>.get` returns `null` for missing keys. If the return type is a boxed primitive, you're one unbox away from NPE. `getOrDefault` is the idiomatic fix. The same trap exists with autounboxing in arithmetic (`int x = map.get(k) + 1`) and comparisons.
