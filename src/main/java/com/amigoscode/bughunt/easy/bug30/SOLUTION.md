# Bug 30 — Reusing a consumed Stream

**Bug:** A `Stream` is single-use. `pipeline.count()` is a *terminal* operation — it closes the stream. The next call (`pipeline.collect(...)`) throws `IllegalStateException: stream has already been operated upon or closed`.

**Fix:** Collect once, then derive both values:
```java
List<String> filtered = entries.stream()
        .filter(e -> e != null)
        .filter(e -> !e.isBlank())
        .map(String::trim)
        .toList();

long count = filtered.size();
String joined = String.join(", ", filtered);
```

**Lesson:** A Stream is a pipeline, not a collection. Once consumed, it's done. If you need multiple results, materialise into a `List` (or `Collector`) and work from that.
