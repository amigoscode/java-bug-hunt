# Bug 37 — Missing defensive copy in constructor

**Bug:** The constructor stores the caller's reference (`this.tags = tags`). The caller still holds a pointer to the same list — any modification they make leaks into the profile.

**Fix:** Copy on the way in:
```java
this.tags = List.copyOf(tags);          // unmodifiable copy
// or
this.tags = new ArrayList<>(tags);       // mutable copy
```

**Lesson:** When a class stores a reference to a mutable argument, ownership must be clear. Either (a) take a copy, or (b) document that the caller must not retain or mutate the argument. Option (a) is almost always safer. Same principle as bug 31 / 35 — encapsulation only works if you don't hand out references to your internals *or* hold references from outsiders.
