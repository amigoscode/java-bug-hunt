# Bug 98 -- List.of does not allow null elements

**Bug:** The `TagList` constructor uses `List.of(tags)` to store the tags. `List.of()` (Java 9+) does not permit `null` elements -- it throws `NullPointerException` immediately if any element is `null`. This means the `TagList` cannot gracefully handle `null` tags.

```java
// Buggy:
public TagList(String... tags) {
    this.tags = List.of(tags);  // throws NPE if any tag is null
}
```

**Fix:** Filter out null elements before creating the list:

```java
// Option 1: filter nulls and collect to unmodifiable list
public TagList(String... tags) {
    this.tags = Arrays.stream(tags)
            .filter(Objects::nonNull)
            .toList();
}

// Option 2: use ArrayList which allows nulls (if nulls are needed)
public TagList(String... tags) {
    this.tags = new ArrayList<>(Arrays.asList(tags));
}
```

**Lesson:** `List.of()`, `Set.of()`, and `Map.of()` (Java 9+) all prohibit `null` elements and keys/values. This is by design to prevent subtle NPE bugs later. If your data may contain nulls, either filter them before creating the collection, or use `ArrayList`/`HashSet`/`HashMap` which do allow nulls. Always check the Javadoc for null-handling behavior of collection factory methods.
