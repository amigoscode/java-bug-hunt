# Bug 60 — Iterator consumed on first use; second traversal sees nothing

**Bug:** The `CsvReport` stores an `Iterator<String>`, which is single-use. Calling `rowCount()` consumes every element. When `headers()` or `dataRows()` is called next, `rows.hasNext()` returns `false` immediately, so they return empty results.

**Fix:** Materialize the iterator into a `List` in the constructor so it can be traversed multiple times:
```java
public class CsvReport {
    private final List<String> rows;

    public CsvReport(Iterator<String> rows) {
        List<String> list = new ArrayList<>();
        rows.forEachRemaining(list::add);
        this.rows = List.copyOf(list);
    }

    public int rowCount() {
        return rows.size();
    }

    public List<String> headers() {
        if (rows.isEmpty()) return List.of();
        return List.of(rows.get(0).split(","));
    }
}
```

Alternatively, accept `Iterable<String>` or `List<String>` instead of `Iterator<String>`.

**Lesson:** `Iterator` is a one-shot cursor — once exhausted, it cannot be reset. If you need to traverse the data more than once, either materialize it into a `List`, accept an `Iterable` (which can create fresh iterators), or restructure your API so a single pass suffices. This bug often appears when code passes `stream.iterator()` or a file-reading iterator to a class that assumes random access.
