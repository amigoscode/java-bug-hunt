# Bug 89 -- HashMap does not preserve insertion order

**Bug:** `FormRenderer` stores fields in a `HashMap`, but the `render()` and `labelSummary()` methods assume fields will be iterated in insertion order. `HashMap` makes no guarantees about iteration order -- it is determined by the internal hash table structure and can vary between JVM versions, runs, and even map sizes.

```java
// Buggy:
private final Map<String, String> fields = new HashMap<>();
```

**Fix:** Use `LinkedHashMap`, which maintains a doubly-linked list of entries in insertion order:

```java
// Fixed:
private final Map<String, String> fields = new LinkedHashMap<>();
```

**Lesson:** Choose your `Map` implementation based on iteration-order requirements:
- `HashMap` -- no order guarantee (fastest for lookups)
- `LinkedHashMap` -- insertion order preserved
- `TreeMap` -- sorted by key (natural order or custom comparator)

If your code depends on iteration order, `HashMap` is the wrong choice. This bug is especially sneaky because small `HashMap` instances may happen to preserve insertion order on some JVM versions, making tests pass intermittently.
