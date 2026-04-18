# Bug 51 — Shallow copy shares mutable list

**Bug:** `copy()` passes the same `items` reference to the new Order. Both orders share one list — adding to one modifies the other.

**Fix:** Copy the list:
```java
public Order copy(String newOrderId) {
    return new Order(newOrderId, customer, new ArrayList<>(items));
}
```

**Lesson:** A "copy" that shares mutable state isn't a copy — it's an alias. When copying objects with collection fields, always create a new collection. For deeper nesting, you'd need a deep copy. Records help here because their fields are typically immutable, but `List` is mutable regardless of what holds a reference to it.
