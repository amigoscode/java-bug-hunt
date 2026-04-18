# Bug 86 -- Collections.singletonList returns an immutable list

**Bug:** The constructor uses `Collections.singletonList(defaultNotification)` to initialise the internal list. This returns a fixed-size, immutable list. Any call to `add()`, `remove()`, or `set()` throws `UnsupportedOperationException`.

```java
// Buggy:
this.notifications = Collections.singletonList(defaultNotification);
```

**Fix:** Use a mutable `ArrayList` instead:

```java
// Fixed:
this.notifications = new ArrayList<>();
this.notifications.add(defaultNotification);
```

**Lesson:** `Collections.singletonList()`, `Collections.unmodifiableList()`, and `List.of()` all return immutable lists. If you need to modify the list later, you must use a mutable implementation like `ArrayList`. A common pattern is `new ArrayList<>(List.of(element))` if you want a concise one-liner that is still mutable.
