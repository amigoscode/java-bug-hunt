# Bug 11 — Switch fallthrough

**Bug:** The `case "L":` branch is missing `break;`. Execution falls through and overwrites `price` with `4.50` — the XL price.

**Fix:** Add `break;` after `price = 3.50;`.

**Lesson:** Java's `switch` statement falls through by default. Every `case` that shouldn't continue into the next must `break;` (or `return;`). Modern alternative: use switch *expressions* with `->` which don't fall through:
```java
double price = switch (size) {
    case "S" -> 2.50;
    case "M" -> 3.00;
    case "L" -> 3.50;
    case "XL" -> 4.50;
    default -> throw new IllegalArgumentException("Unknown size: " + size);
};
```
