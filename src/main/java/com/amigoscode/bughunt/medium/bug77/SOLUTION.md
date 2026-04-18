# Bug 77 -- String.chars() produces IntStream, not Stream\<Character\>

**Bug:** `String.chars()` returns an `IntStream`. Calling `.boxed()` on an `IntStream` produces `Stream<Integer>`, not `Stream<Character>`. When the result is collected into a `Set`, it becomes `Set<Integer>`. Later, `containsChar('h')` autoboxes the `char` to `Character`, and `Set<Integer>.contains(Character)` always returns `false` because `Integer` and `Character` are different types that are never equal.

```java
// Buggy:
public Set<Object> uniqueCharacters() {
    return text.chars()         // IntStream
        .boxed()                // Stream<Integer>  (not Stream<Character>!)
        .collect(Collectors.toSet());  // Set<Integer>
}

public boolean containsChar(char c) {
    return uniqueCharacters().contains(c);  // contains(Character) on Set<Integer> -> false
}
```

**Fix:** Map the int values to `Character` objects before collecting:

```java
public Set<Object> uniqueCharacters() {
    return text.chars()
        .mapToObj(c -> (char) c)    // Stream<Character>
        .collect(Collectors.toSet());
}
```

**Lesson:** `String.chars()` is one of Java's most common stream pitfalls. It returns `IntStream` (code points as ints), not `Stream<Character>`. Use `.mapToObj(c -> (char) c)` to convert to actual `Character` objects. The type erasure in the `Set<Object>` return type hides the mismatch at compile time, but `Integer.equals(Character)` is always false at runtime.
