# Bug 96 -- Stream.toList() returns an unmodifiable list

**Bug:** The `sortedNames`, `sortedNamesOlderThan`, and `sortedUpperCaseNames` methods collect stream results with `.toList()` (Java 16+), which returns an unmodifiable list. They then call `Collections.sort()` on that list, which throws `UnsupportedOperationException` because the list cannot be modified in place.

```java
// Buggy:
List<String> names = people.stream()
        .map(Person::name)
        .toList();          // returns unmodifiable list
Collections.sort(names);   // UnsupportedOperationException!
```

**Fix:** Either sort within the stream using `.sorted()`, or collect into a mutable list with `Collectors.toList()` or `new ArrayList<>(...)`:

```java
// Option 1: sort in the stream
List<String> names = people.stream()
        .map(Person::name)
        .sorted()
        .toList();

// Option 2: collect into a mutable list first
List<String> names = new ArrayList<>(people.stream()
        .map(Person::name)
        .toList());
Collections.sort(names);
```

**Lesson:** `Stream.toList()` (Java 16+) is not the same as `.collect(Collectors.toList())`. The former returns an unmodifiable list, while the latter currently returns a mutable `ArrayList` (though this is not guaranteed by the spec). If you need to modify the resulting list, either sort within the stream pipeline or explicitly collect into a mutable list.
