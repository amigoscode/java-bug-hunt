# Bug 21 — `==` on boxed `Integer`

**Bug:** `a == b` compares *references*, not values. Java caches boxed `Integer` instances for `-128..127`, so `10 == 10` happens to be `true` (same cached object). But `5000 == 5000` creates two distinct objects — reference comparison returns `false`.

**Fix:**
```java
return a.equals(b);
// or
return a.intValue() == b.intValue();
```

**Lesson:** For any boxed numeric type (`Integer`, `Long`, `Double`, …) use `.equals()` or unbox explicitly. The fact that small values *accidentally* work makes this bug especially dangerous — you can't catch it without testing values outside the cache.
