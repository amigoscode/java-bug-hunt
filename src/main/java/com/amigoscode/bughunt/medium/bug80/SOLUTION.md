# Bug 80 -- Collecting to unmodifiable map then trying to mutate

**Bug:** The constructor collects permission entries using `Collectors.toUnmodifiableMap()`, which produces an immutable map. Any subsequent call to `grant()` (which calls `perms.put()`) or `revoke()` (which calls `perms.remove()`) throws `UnsupportedOperationException` at runtime.

```java
// Buggy:
this.perms = entries.collect(
    Collectors.toUnmodifiableMap(Entry::key, Entry::value));

public void grant(String key, String value) {
    perms.put(key, value);  // UnsupportedOperationException!
}
```

**Fix:** Use a mutable map collector instead:

```java
// Option 1: collect into a HashMap
this.perms = entries.collect(
    Collectors.toMap(Entry::key, Entry::value,
        (a, b) -> b, HashMap::new));

// Option 2: collect to map and wrap
this.perms = new HashMap<>(entries.collect(
    Collectors.toMap(Entry::key, Entry::value)));
```

**Lesson:** `Collectors.toUnmodifiableMap()` (added in Java 10) and `Collectors.toUnmodifiableList()` / `toUnmodifiableSet()` produce truly immutable collections. This is great when you want immutability, but if your design requires later mutation (add/remove/update), you must use a standard mutable collector or copy the result into a mutable collection. The compiler will not warn you -- the `Map` interface includes `put()` and `remove()`, but the unmodifiable implementation throws at runtime.
