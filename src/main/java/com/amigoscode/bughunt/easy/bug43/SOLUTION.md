# Bug 43 — Recursion missing the "self" contribution

**Bug:** `depth()` returns `max` — the deepest subtree's depth — but forgets to add `1` for the *current* directory. Every level above the leaves is one short.

**Fix:**
```java
return max + 1;
```

**Lesson:** When recursion aggregates a value that includes "this node plus the children", the +1 (or other combining step) must happen at *every* level. A good sanity check: does the formula work for the base case? Here the leaf returns `1` correctly — so the recursive case needs to preserve that (`max + 1`) to stay consistent.
