# Bug 23 — `Arrays.asList` returns a fixed-size list

**Bug:** `Arrays.asList(defaults)` wraps the array — the returned list is *fixed-size*. Calling `add` or `remove` throws `UnsupportedOperationException`.

**Fix:** Copy into a resizable list:
```java
this.roles = new ArrayList<>(Arrays.asList(defaults));
// or, since Java 9:
this.roles = new ArrayList<>(List.of(defaults));
```

**Lesson:** `Arrays.asList`, `List.of`, `List.copyOf`, and `Collections.unmodifiableList` all return lists that reject mutation (though for different reasons). If you need to mutate, wrap them in `new ArrayList<>(...)`.
