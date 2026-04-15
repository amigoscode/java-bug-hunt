# Bug 19 — `String.split` takes a regex

**Bug:** `ip.split(".")` splits on the regex `.`, which matches *any character*. The result is an array of all-empty strings (or empty array depending on JDK), and parsing/validation fails.

**Fix:** Escape the dot:
```java
String[] parts = ip.split("\\.");
```
Or use `Pattern.quote(".")` to escape programmatically.

**Lesson:** `String.split`, `String.replaceAll`, `String.matches` all take regex. Characters like `.`, `|`, `*`, `+`, `(`, `)` are special. For literal matching, prefer `String.replace` (no regex) or escape carefully.
