# Bug 64 — replaceAll treats placeholder as regex

**Bug:** The `render` method uses `String.replaceAll("${" + key + "}", value)`, but `replaceAll` interprets its first argument as a regular expression. The characters `$`, `{`, and `}` are regex metacharacters. `${` is an invalid regex construct, so this throws a `PatternSyntaxException` at runtime.

**Fix:** Use `String.replace()` instead of `String.replaceAll()`. The `replace` method treats both arguments as literal strings:

```java
result = result.replace("${" + entry.getKey() + "}", entry.getValue());
```

Alternatively, escape the regex pattern:

```java
String escapedKey = Pattern.quote("${" + entry.getKey() + "}");
result = result.replaceAll(escapedKey, Matcher.quoteReplacement(entry.getValue()));
```

But `replace()` is simpler and more appropriate when you want literal string replacement.

**Lesson:** `String.replace()` does literal replacement; `String.replaceAll()` uses regex. This is a common source of bugs, especially when the search string contains characters that have special meaning in regex (`$`, `.`, `*`, `+`, `?`, `{`, `}`, `[`, `]`, `(`, `)`, `\`, `^`, `|`). When in doubt, use `replace()` for literal text and `replaceAll()` only when you actually need pattern matching.
