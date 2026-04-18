# Bug 76 -- Enum.valueOf is case-sensitive

**Bug:** The `parse` method calls `Color.valueOf(input)` directly with the user's input. `Enum.valueOf` is case-sensitive and requires the exact constant name (e.g. `"RED"`). When a user types `"red"` or `"Red"`, it throws an `IllegalArgumentException`.

```java
// Buggy:
public Color parse(String input) {
    return Color.valueOf(trimmed);  // "red" throws IllegalArgumentException
}
```

**Fix:** Convert the input to uppercase before calling `valueOf`, or use a case-insensitive lookup map:

```java
// Option 1: uppercase conversion
public Color parse(String input) {
    return Color.valueOf(trimmed.toUpperCase());
}

// Option 2: static lookup map
private static final Map<String, Color> LOOKUP =
    Arrays.stream(Color.values())
        .collect(Collectors.toMap(
            c -> c.name().toLowerCase(), c -> c));

public Color parse(String input) {
    Color color = LOOKUP.get(trimmed.toLowerCase());
    if (color == null) throw new IllegalArgumentException("Unknown colour: " + input);
    return color;
}
```

**Lesson:** `Enum.valueOf` is a direct name-to-constant lookup -- it does not perform any normalisation. When accepting user input, always normalise case before looking up enum constants. A pre-built map is more efficient for repeated lookups and gives you control over error messages.
