# Bug 20 — Unchecked `indexOf` return value

**Bug:** `indexOf('@')` returns `-1` if the character is not found. Then `substring(-1 + 1) = substring(0)` — which surprisingly *doesn't* throw, it just returns the whole string. And `localPart` calls `substring(0, -1)` which throws `StringIndexOutOfBoundsException` — the wrong exception type.

**Fix:**
```java
int at = email.indexOf('@');
if (at < 0) {
    throw new IllegalArgumentException("Email missing '@': " + email);
}
return email.substring(at + 1).toLowerCase();
```

**Lesson:** Always check `indexOf`'s return for `-1`. Throw the *right* exception type with a useful message — `IllegalArgumentException` for invalid input, not the implementation-leaked `StringIndexOutOfBoundsException`.
