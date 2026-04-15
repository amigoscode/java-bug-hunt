# Bug 48 — Wrong comparison for inclusive range

**Bug:** The fields are named `minInclusive` / `maxInclusive`, but `contains` uses strict `>` and `<`. Age 18 and age 65 should be inside an "adult" range, but they're rejected.

**Fix:**
```java
return age >= minInclusive && age <= maxInclusive;
```

**Lesson:** When your field / parameter names encode inclusivity (or any contract), the implementation must match. This is where *naming is testing*: `minInclusive` makes the bug obvious at the call site. Always test the exact boundary values — they're where half of range bugs live.
