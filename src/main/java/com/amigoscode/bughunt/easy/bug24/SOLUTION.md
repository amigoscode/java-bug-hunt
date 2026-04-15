# Bug 24 — Modifying a list during iteration

**Bug:** The enhanced for-loop uses an `Iterator` under the hood. `items.remove(item)` structurally modifies the list — next call to `iterator.next()` throws `ConcurrentModificationException`.

**Fix — pick one:**
```java
// 1. Iterator.remove
Iterator<String> it = items.iterator();
while (it.hasNext()) {
    String item = it.next();
    if (item.toLowerCase().contains(substring.toLowerCase())) {
        it.remove();
    }
}

// 2. removeIf (cleanest)
items.removeIf(i -> i.toLowerCase().contains(substring.toLowerCase()));

// 3. Collect-then-remove
List<String> toRemove = items.stream()
    .filter(i -> i.toLowerCase().contains(substring.toLowerCase()))
    .toList();
items.removeAll(toRemove);
```

**Lesson:** Structural modification during iteration is forbidden in Java's standard collections. `removeIf` is almost always the cleanest answer.
