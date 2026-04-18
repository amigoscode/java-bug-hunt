# Bug 75 -- String.format locale sensitivity

**Bug:** The `formatRow` method uses `String.format("%.2f", score)` without specifying a locale. `String.format` uses `Locale.getDefault()`, which varies by system. In locales that use a comma as the decimal separator (German, French, Spanish, etc.), the output becomes `"1,50"` instead of `"1.50"`. Since comma is also the CSV field delimiter, this corrupts the CSV structure -- a row like `"Alice,1,50"` appears to have three fields instead of two.

```java
// Buggy:
return name + "," + String.format("%.2f", score);
// In GERMAN locale: "Alice,1,50" -- broken CSV!
```

**Fix:** Always specify `Locale.US` (or `Locale.ROOT`) when formatting numbers for machine-readable output:

```java
// Fixed:
return name + "," + String.format(Locale.US, "%.2f", score);
// Always produces: "Alice,1.50"
```

**Lesson:** `String.format`, `String.formatted`, and `Formatter` are all locale-sensitive by default. For human-facing output, the default locale is usually correct. But for machine-readable formats (CSV, JSON, XML, URLs, HTTP headers), always use `Locale.US` or `Locale.ROOT` to ensure consistent decimal points, digit grouping, and number formatting regardless of where the code runs.
