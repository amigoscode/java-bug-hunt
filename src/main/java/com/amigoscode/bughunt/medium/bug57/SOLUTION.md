# Bug 57 — Unchecked generic cast ignores type parameter

**Bug:** The `get` method casts the stored `Object` to `(T)`, but due to type erasure `T` is just `Object` at runtime. The `Class<T> type` parameter is accepted but never used for validation. A `String` stored under `"age"` can be retrieved as `Integer` without error — the `ClassCastException` only surfaces later when the caller tries to use the value as an `Integer`.

**Fix:** Use `Class.cast()` which performs a runtime type check:
```java
public <T> T get(String key, Class<T> type) {
    Object value = store.get(key);
    if (value == null) {
        return null;
    }
    return type.cast(value);
}
```

**Lesson:** A generic cast `(T)` is erased to `(Object)` at runtime and never throws. If you have a `Class<T>` token, use `type.cast(value)` or `type.isInstance(value)` to get real runtime type checking. The `@SuppressWarnings("unchecked")` annotation is a code smell — it means the compiler is warning you the cast is not verified.
