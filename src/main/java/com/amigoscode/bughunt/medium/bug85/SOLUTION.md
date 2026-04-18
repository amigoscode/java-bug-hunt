# Bug 85 -- String.join converts null to the literal "null"

**Bug:** `String.join(",", fields)` does not skip or blank out null elements. Instead, it calls `String.valueOf(element)` on each element, which converts `null` to the four-character string `"null"`. So `build("a", null, "c")` produces `"a,null,c"` instead of the expected `"a,,c"`.

```java
// Buggy:
public String build(String... fields) {
    return String.join(delimiter, fields);  // null → "null"
}
```

**Fix:** Replace nulls with empty strings before joining:

```java
// Fixed (option 1 -- stream):
public String build(String... fields) {
    return Arrays.stream(fields)
        .map(f -> f == null ? "" : f)
        .collect(Collectors.joining(delimiter));
}

// Fixed (option 2 -- StringJoiner):
public String build(String... fields) {
    StringJoiner joiner = new StringJoiner(delimiter);
    for (String f : fields) {
        joiner.add(f == null ? "" : f);
    }
    return joiner.toString();
}
```

**Lesson:** `String.join` and `StringJoiner.add` both convert null to `"null"` via `String.valueOf`. This is documented behaviour but frequently surprising. If your domain treats null fields as "absent" (empty between delimiters), you must sanitise nulls before joining. The class already has a `sanitise` method that does this correctly -- the `build` method should have used it.
