# Bug 40 — Unboxing `null` when incrementing a map counter

**Bug:** `counts.get(clean)` returns `null` the first time the word is seen. Then `current + 1` unboxes `null` → `NullPointerException`.

**Fix — pick one:**
```java
// 1. Null-check:
counts.put(clean, current == null ? 1 : current + 1);

// 2. getOrDefault:
counts.put(clean, counts.getOrDefault(clean, 0) + 1);

// 3. merge (idiomatic):
counts.merge(clean, 1, Integer::sum);

// 4. computeIfAbsent + increment (if value is mutable, e.g. int[]):
counts.compute(clean, (k, v) -> v == null ? 1 : v + 1);
```

**Lesson:** Building a count map is a classic — and `Map.merge` is the right tool. It handles the null case, the update, and reads cleanly. Bonus: for concurrent counters, use `ConcurrentHashMap` + `merge` or `AtomicInteger`.
