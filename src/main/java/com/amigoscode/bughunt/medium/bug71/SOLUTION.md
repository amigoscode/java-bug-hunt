# Bug 71 -- Stream.flatMap null handling

**Bug:** The `allTags` method (and `tagCounts` / `totalTagUsages`) calls `a.tags().stream()` inside `flatMap`. When an article has `null` tags, `a.tags()` returns `null`, and calling `.stream()` on `null` throws a `NullPointerException`.

```java
// Buggy:
return articles.stream()
        .flatMap(a -> a.tags().stream())  // NPE when tags() is null
        .distinct()
        .sorted()
        .toList();
```

**Fix:** Guard against null tags by substituting an empty stream:

```java
// Option 1: ternary guard
return articles.stream()
        .flatMap(a -> a.tags() == null ? Stream.empty() : a.tags().stream())
        .distinct()
        .sorted()
        .toList();

// Option 2: filter first
return articles.stream()
        .filter(a -> a.tags() != null)
        .flatMap(a -> a.tags().stream())
        .distinct()
        .sorted()
        .toList();

// Option 3: use Optional
return articles.stream()
        .flatMap(a -> Optional.ofNullable(a.tags()).stream().flatMap(List::stream))
        .distinct()
        .sorted()
        .toList();
```

**Lesson:** When using `flatMap`, always consider whether the mapping function can receive or produce `null`. Unlike `map`, `flatMap` expects a non-null `Stream` return value. Nullable collections from record accessors or getters are a common source of NPEs inside stream pipelines.
