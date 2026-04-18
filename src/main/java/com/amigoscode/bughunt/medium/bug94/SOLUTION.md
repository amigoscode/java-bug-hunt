# Bug 94 -- Map.merge null value

**Bug:** The `mergeFrom` method uses `Map.merge(key, value, Integer::sum)` to combine word counts. However, `Map.merge` explicitly throws `NullPointerException` if the `value` argument is null. If the source map contains any key mapped to null, the merge call fails immediately.

```java
// Buggy:
merged.merge(entry.getKey(), entry.getValue(), Integer::sum);
// If entry.getValue() is null -> NullPointerException!
```

**Fix:** Filter out or default null values before merging:

```java
// Option 1: skip null values
for (Map.Entry<String, Integer> entry : source.entrySet()) {
    Integer value = entry.getValue();
    if (value != null) {
        merged.merge(entry.getKey(), value, Integer::sum);
    }
}

// Option 2: treat null as zero
for (Map.Entry<String, Integer> entry : source.entrySet()) {
    int value = entry.getValue() != null ? entry.getValue() : 0;
    if (value != 0) {
        merged.merge(entry.getKey(), value, Integer::sum);
    }
}

// Option 3: use compute instead
for (Map.Entry<String, Integer> entry : source.entrySet()) {
    merged.compute(entry.getKey(), (k, existing) -> {
        int newVal = entry.getValue() != null ? entry.getValue() : 0;
        return (existing != null ? existing : 0) + newVal;
    });
}
```

**Lesson:** `Map.merge` has strict null handling: the value parameter must not be null (throws NPE), and if the remapping function returns null, the key is removed. When merging data from external sources that may contain null values, always validate or default before calling `merge`.
