# Bug 38 — `Integer.parseInt` doesn't trim

**Bug:** `Integer.parseInt("  42  ")` throws `NumberFormatException`. Surrounding whitespace is not allowed — only an optional leading `+`/`-` and digits.

**Fix:** Trim before parsing:
```java
return Integer.parseInt(input.trim());
```
Or normalise once in `parse`:
```java
public int parse(String input) {
    if (input == null) return defaultValue;
    return Integer.parseInt(input.trim());
}
```

**Lesson:** Javadoc is the source of truth. `parseInt` is strict. If you accept user input, normalise it (trim, strip grouping separators) *before* parsing. Consider `Scanner` or `NumberFormat` for more lenient parsing of locale-formatted numbers.
