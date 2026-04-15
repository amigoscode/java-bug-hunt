# Bug 15 — Wrong format specifier

**Bug:** `%d` expects an integer, but `unitPrice` is a `double`. At runtime this throws `IllegalFormatConversionException: d != java.lang.Double`.

**Fix:** Use `%.2f` (or any float-compatible specifier) for `unitPrice`:
```java
String.format("%-12s x%d @ $%.2f = $%.2f", item, quantity, unitPrice, lineTotal);
```

**Lesson:** `String.format` specifiers are type-sensitive. `%d` = integer, `%f` = floating-point, `%s` = any object (calls `toString`). Turn on IDE inspections — IntelliJ flags these at compile time.
