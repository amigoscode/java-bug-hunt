# Bug 34 — Regex too narrow for `matches()`

**Bug:** `matches()` requires the regex to match the *entire* string. The pattern `https?://` matches only the protocol prefix — it doesn't match `https://example.com` in full, so `isValidUrl` returns `false`.

**Fix:** Widen the pattern to cover the whole URL:
```java
private static final String URL_REGEX = "https?://\\S+";
```
Or use `find()` if you want a "contains a URL" test:
```java
pattern.matcher(candidate).find();
```

**Lesson:** `matches()` = full-match (anchored implicitly). `find()` = searches for a match anywhere. Know which one you want. The symmetry with `String.matches` is the same — always full-match.
