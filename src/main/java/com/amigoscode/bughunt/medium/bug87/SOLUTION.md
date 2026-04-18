# Bug 87 -- Iterator.remove() called without next()

**Bug:** The `removeByPrefix` method calls `it.hasNext()` inside the loop to check the condition, but never calls `it.next()`. The `Iterator` contract requires `next()` to be called exactly once before each `remove()`. Calling `remove()` without a preceding `next()` throws `IllegalStateException`.

```java
// Buggy:
Iterator<String> it = items.iterator();
while (it.hasNext()) {
    if (it.hasNext() && items.get(0).startsWith(prefix)) {
        it.remove();  // IllegalStateException -- no next() was called
    }
}
```

**Fix:** Call `it.next()` to advance the iterator and use the returned element for the prefix check:

```java
// Fixed:
Iterator<String> it = items.iterator();
while (it.hasNext()) {
    String element = it.next();
    if (element.startsWith(prefix)) {
        it.remove();
    }
}
```

**Lesson:** `Iterator.hasNext()` only peeks ahead -- it does not advance the cursor. You must call `next()` to advance to the current element before `remove()` can operate on it. Also note the second bug: the original code checked `items.get(0)` instead of the current element, which would have only ever checked the first element of the list.
