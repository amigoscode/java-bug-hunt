# Bug 74 -- Stream.peek for side effects

**Bug:** The `firstMatching` method uses `peek(i -> count++)` to count how many elements are processed. However, `findFirst()` is a short-circuiting terminal operation -- it stops consuming the stream as soon as it finds a match. This means `peek()` only executes for the elements actually visited before (and including) the first match, not for the entire stream.

```java
// Buggy:
return items.stream()
        .peek(i -> count++)        // only runs for elements consumed
        .filter(i -> i.startsWith(prefix))
        .findFirst();              // short-circuits on first match
```

**Fix:** If you need to count all items, do it separately or use a non-short-circuiting approach:

```java
// Option 1: count separately
public Optional<String> firstMatching(List<String> items, String prefix) {
    count = items.size();  // we know all items exist
    return items.stream()
            .filter(i -> i.startsWith(prefix))
            .findFirst();
}

// Option 2: use a full traversal instead of findFirst
public Optional<String> firstMatching(List<String> items, String prefix) {
    count = 0;
    List<String> matches = items.stream()
            .peek(i -> count++)
            .filter(i -> i.startsWith(prefix))
            .toList();  // non-short-circuiting -- processes all elements
    return matches.isEmpty() ? Optional.empty() : Optional.of(matches.get(0));
}
```

**Lesson:** `Stream.peek()` is designed for debugging, not for reliable side effects. Its Javadoc explicitly warns that it "exists mainly to support debugging" and that "the action may not be invoked for some elements" with short-circuiting operations like `findFirst`, `findAny`, `anyMatch`, or `limit`. Never rely on `peek()` for business logic.
