# Bug 70 -- Map.putIfAbsent returns null for new entries

**Bug:** The `registerOrGet` method returns the result of `users.putIfAbsent(id, name)` directly. However, `putIfAbsent` returns `null` when the key was not previously in the map (meaning the insertion succeeded). It only returns the existing value when the key was already present. So for every new user registration, the method returns `null` instead of the user's name.

```java
// Buggy:
public String registerOrGet(long id, String name) {
    return users.putIfAbsent(id, name); // returns null for new entries!
}
```

**Fix:** Handle the null return value by falling back to the provided name:

```java
// Option 1: check the return value
public String registerOrGet(long id, String name) {
    String existing = users.putIfAbsent(id, name);
    return existing != null ? existing : name;
}

// Option 2: use merge or compute
public String registerOrGet(long id, String name) {
    return users.merge(id, name, (oldVal, newVal) -> oldVal);
}
```

**Lesson:** `Map.putIfAbsent` has a counterintuitive return value contract. It returns the *previous* value associated with the key, or `null` if there was none. This is consistent with `Map.put`, but easy to misread as "returns the value that is now stored." Always check the Javadoc for Map methods -- `put`, `putIfAbsent`, `compute`, `merge`, and `replace` all have subtly different return semantics.
