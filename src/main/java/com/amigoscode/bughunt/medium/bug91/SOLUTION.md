# Bug 91 -- Autoboxing cache in Long

**Bug:** The `isSameSession` method compares two `Long` objects using `==`. Java caches `Long` instances for values in the range -128 to 127, so `==` works by coincidence for small IDs. For values outside that range, `Long.valueOf(1000L) == Long.valueOf(1000L)` returns `false` because they are distinct objects on the heap.

```java
// Buggy:
public boolean isSameSession(Long sessionId1, Long sessionId2) {
    return sessionId1 == sessionId2; // reference comparison!
}
```

**Fix:** Use `.equals()` to compare boxed numeric types, or unbox to primitive `long`:

```java
// Option 1: use .equals()
public boolean isSameSession(Long sessionId1, Long sessionId2) {
    return sessionId1.equals(sessionId2);
}

// Option 2: unbox to long
public boolean isSameSession(Long sessionId1, Long sessionId2) {
    return sessionId1.longValue() == sessionId2.longValue();
}
```

**Lesson:** Never use `==` to compare boxed numeric types (`Integer`, `Long`, `Short`, `Byte`, `Character`). The JVM caches small values, so `==` appears to work in unit tests with small numbers, but breaks in production with larger values. Always use `.equals()` or unbox explicitly.
