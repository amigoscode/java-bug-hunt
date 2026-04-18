# Bug 55 — Builder reuse carries state across builds

**Bug:** `build()` does not reset the internal state. The `conditions` list accumulates across multiple `build()` calls, so the second query includes WHERE conditions from the first. While `select` and `from` are overwritten (simple assignment), `conditions` is appended to via `where()`, so old conditions persist.

**Fix:** Clear the conditions (and other state) at the start of a new query chain, or have `build()` reset the state after producing the query:
```java
public String build() {
    // ... build the query ...
    String result = sql.toString();
    
    // Reset state for next use
    selectClause = null;
    fromClause = null;
    conditions.clear();
    orderByClause = null;
    limitValue = null;
    
    return result;
}
```

Or better yet, make the builder produce immutable query objects and require a new builder per query.

**Lesson:** Mutable builders that are reused can leak state between builds. Either reset all state after `build()`, or follow the pattern where each `build()` consumes the builder (making it single-use). The safest approach is to create a new builder for each query.
