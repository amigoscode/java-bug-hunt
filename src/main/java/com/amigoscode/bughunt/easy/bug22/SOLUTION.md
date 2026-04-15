# Bug 22 — `List.remove(int)` vs `List.remove(Object)`

**Bug:** `taskIds.remove(taskId)` with a primitive `int` resolves to `remove(int index)` — it tries to remove the element at *position* `taskId`. Calling `cancel(20)` on a 3-element list throws `IndexOutOfBoundsException`.

**Fix:** Force the Object overload by boxing:
```java
taskIds.remove(Integer.valueOf(taskId));
```

**Lesson:** `List<Integer>` has both `remove(int)` (by index) and `remove(Object)` (by value). Java resolves overloads at compile time — a primitive `int` always picks `remove(int)`. When your list's element type is `Integer` (or `Long`), always box explicitly for value-based removal. Same trap with `indexOf`, `contains`, etc. — actually those only take `Object`, but `remove` is the notorious one.
