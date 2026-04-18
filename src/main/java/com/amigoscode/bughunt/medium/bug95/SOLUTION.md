# Bug 95 -- Null unboxing from Map.get on missing key

**Bug:** The `cost` method looks up a region in a `Map<String, Double>` and returns the result as a primitive `double`. When the region is not in the map, `Map.get()` returns `null`. Java then tries to auto-unbox `null` to `double`, which throws `NullPointerException`.

```java
// Buggy:
public double cost(String region) {
    return regionCosts.get(region); // null for unknown region -> NPE on unbox
}
```

**Fix:** Use `getOrDefault` to provide a fallback, or check for null:

```java
// Option 1: use getOrDefault with a default rate
public double cost(String region) {
    return regionCosts.getOrDefault(region, 15.99); // international fallback
}

// Option 2: explicit null check with descriptive exception
public double cost(String region) {
    Double rate = regionCosts.get(region);
    if (rate == null) {
        throw new IllegalArgumentException("Unsupported region: " + region);
    }
    return rate;
}
```

**Lesson:** Auto-unboxing a `null` wrapper type (`Double`, `Integer`, `Long`, etc.) to a primitive always throws `NullPointerException`. This is a common trap when using `Map.get()` with a primitive return type. Always use `getOrDefault`, `containsKey`, or an explicit null check before unboxing. The compiler cannot warn about this because the unboxing is implicit.
