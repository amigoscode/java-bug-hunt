# Bug 41 — Stack popping from wrong end

**Bug:** `push` appends to the end of the `ArrayList`, but `pop` and `peek` remove from index `0`. That's FIFO (queue behaviour), not LIFO (stack behaviour).

**Fix:** Operate on the last element:
```java
public int pop() {
    if (data.isEmpty()) throw new NoSuchElementException("...");
    return data.remove(data.size() - 1);
}
public int peek() {
    if (data.isEmpty()) throw new NoSuchElementException("...");
    return data.get(data.size() - 1);
}
```
Bonus — using `removeFirst`/`removeLast` (Java 21+) or `java.util.Deque` makes the intent explicit and avoids O(n) shifting from index 0.

**Lesson:** `ArrayList.remove(0)` is O(n) — every other element shifts. For stack/queue use a `Deque` (e.g. `ArrayDeque`): its O(1) operations on both ends make the intent clearer.
