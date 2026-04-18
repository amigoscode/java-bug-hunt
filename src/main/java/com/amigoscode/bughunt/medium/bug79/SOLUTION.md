# Bug 79 -- Pattern.matcher().matches() vs .find()

**Bug:** The `hasDigit` and `hasUppercase` methods use `Pattern.matcher(password).matches()`. The `matches()` method requires the **entire** input string to match the regex. Since the regex is `\d` (a single digit), `matches()` only returns `true` when the entire password is exactly one digit (e.g. `"5"`). For any real password like `"abc1def"`, `matches()` returns `false` even though the string clearly contains a digit.

```java
// Buggy:
public boolean hasDigit(String password) {
    return DIGIT_PATTERN.matcher(password).matches();
    // "abc1def".matches("\\d") -> false (entire string is not one digit)
}
```

**Fix:** Use `find()` instead of `matches()`. `find()` searches for a match anywhere within the string:

```java
public boolean hasDigit(String password) {
    return DIGIT_PATTERN.matcher(password).find();
    // "abc1def" -> finds '1' -> true
}

public boolean hasUppercase(String password) {
    return UPPER_PATTERN.matcher(password).find();
}
```

**Lesson:** `Matcher.matches()` anchors the regex to the entire string (equivalent to `^...$`). `Matcher.find()` searches for the pattern anywhere in the string. When you want to check "does this string contain X", use `find()`. When you want to check "does this string consist entirely of X", use `matches()`. This is one of the most common regex mistakes in Java.
