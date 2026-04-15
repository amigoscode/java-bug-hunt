# Bug 26 — Comparator direction reversed

**Bug:** `(a, b) -> a.score() - b.score()` sorts *ascending* (smallest first). A leaderboard's top N should be *descending*.

**Fix — pick one:**
```java
sorted.sort((a, b) -> b.score() - a.score());                 // flipped
sorted.sort(Comparator.comparingInt(Entry::score).reversed()); // clearer
```

Bonus: `a.score() - b.score()` can overflow for extreme values. Prefer `Integer.compare(a.score(), b.score())` or `Comparator.comparingInt`.

**Lesson:** Write comparators using `Comparator.comparingX` + `.reversed()` / `.thenComparing` — it's self-documenting and overflow-safe. Reserve subtraction-based lambdas for throwaway cases.
