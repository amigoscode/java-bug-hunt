# Bug 12 — Off-by-one on grade boundaries

**Bug:** Uses `>` instead of `>=`. A score of exactly `90` fails `score > 90` and falls through to the `B` branch. Same for `80`, `70`, `60`.

**Fix:** Use `>=` for all boundary comparisons:
```java
if (score >= 90) return 'A';
else if (score >= 80) return 'B';
...
```

**Lesson:** When grade boundaries are inclusive ("90 and above is an A"), the comparison must be `>=`. Always write a test for the exact boundary value.
