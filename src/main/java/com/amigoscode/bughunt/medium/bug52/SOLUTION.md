# Bug 52 — HashMap with mutable key

**Bug:** `Product` is used as a `HashMap` key, but its `name` and `category` fields are mutable and included in `equals`/`hashCode`. When a field is mutated after insertion, the hash changes and the entry lands in a different bucket — `get()` can no longer find it even though the object is still in the map.

**Fix:** Make `Product` immutable — remove the setters and make the fields `final`:
```java
public static class Product {
    private final String name;
    private final String category;
    private final double price;

    // remove setName() and setCategory()
}
```

Alternatively, use a stable immutable identifier (e.g., a product ID) for `equals`/`hashCode` instead of mutable fields.

**Lesson:** Objects used as `HashMap` or `HashSet` keys must not have their `hashCode` change after insertion. The safest approach is to make key objects immutable. If mutability is required, base `equals`/`hashCode` on an immutable identifier like a UUID or database ID.
