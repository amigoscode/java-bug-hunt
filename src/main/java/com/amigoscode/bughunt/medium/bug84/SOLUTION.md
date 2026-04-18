# Bug 84 -- Optional.orElse eager evaluation vs orElseGet

**Bug:** The `findOrCreate` method uses `Optional.orElse(createDefault())`. The `orElse` method always evaluates its argument, even when the Optional contains a value. This means `createDefault()` is called on every invocation -- incrementing the `defaultsCreated` counter and creating a throwaway object even when an existing user is found.

```java
// Buggy:
public User findOrCreate(long id) {
    return findUser(id).orElse(createDefault());  // createDefault() always runs!
}
```

**Fix:** Use `orElseGet` with a lambda to defer evaluation:

```java
// Fixed:
public User findOrCreate(long id) {
    return findUser(id).orElseGet(() -> createDefault());  // only runs when empty
}
// or equivalently:
public User findOrCreate(long id) {
    return findUser(id).orElseGet(this::createDefault);
}
```

**Lesson:** `Optional.orElse(T)` evaluates its argument eagerly -- the value is computed before `orElse` even checks whether the Optional is present. When the fallback involves side effects (database writes, counter increments, network calls, object creation with cost), use `orElseGet(Supplier<T>)` instead. The supplier is only invoked when the Optional is actually empty. This is one of the most common `Optional` pitfalls in Java.
