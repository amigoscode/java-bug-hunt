# Bug 69 -- Collections.unmodifiableList is a view, not a copy

**Bug:** The `snapshot()` method returns `Collections.unmodifiableList(entries)`, which creates a read-only **view** backed by the original `entries` list. It does not make a copy. Any subsequent changes to the internal `entries` list (via `addEntry`, `clear`, etc.) are visible through the "snapshot", defeating its purpose.

```java
// Buggy:
public List<String> snapshot() {
    return Collections.unmodifiableList(entries); // wraps the live list
}
```

**Fix:** Copy the list before wrapping it, or use `List.copyOf()`:

```java
// Option 1: defensive copy + unmodifiable wrapper
public List<String> snapshot() {
    return Collections.unmodifiableList(new ArrayList<>(entries));
}

// Option 2: List.copyOf (Java 10+), returns an unmodifiable copy
public List<String> snapshot() {
    return List.copyOf(entries);
}
```

**Lesson:** `Collections.unmodifiableList` prevents the *caller* from modifying the list, but it does not prevent the *owner* from modifying the backing list. For a true snapshot, you need a defensive copy. `List.copyOf()` (Java 10+) is the most concise way to get an unmodifiable copy.
