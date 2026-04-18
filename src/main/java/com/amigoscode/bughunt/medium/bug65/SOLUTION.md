# Bug 65 — Comparator.thenComparing result discarded

**Bug:** In `sortByExtensionThenName`, the code calls `comparator.thenComparing(FileEntry::getName)` but never captures the return value. `thenComparing` returns a **new** `Comparator` — it does not modify the original. The sort uses only the extension comparator, so files with the same extension are not sub-sorted by name.

```java
// Buggy code:
Comparator<FileEntry> comparator = Comparator.comparing(FileEntry::getExtension);
comparator.thenComparing(FileEntry::getName);  // result thrown away!
sorted.sort(comparator);  // only sorts by extension
```

**Fix:** Assign the result of `thenComparing` back to the comparator variable:

```java
Comparator<FileEntry> comparator = Comparator.comparing(FileEntry::getExtension)
        .thenComparing(FileEntry::getName);
sorted.sort(comparator);
```

Or capture the result:

```java
Comparator<FileEntry> comparator = Comparator.comparing(FileEntry::getExtension);
comparator = comparator.thenComparing(FileEntry::getName);
sorted.sort(comparator);
```

**Lesson:** `Comparator` is immutable — methods like `thenComparing`, `reversed`, and `thenComparingInt` all return new instances. Discarding the return value silently drops the secondary sort criteria. This bug is easy to miss because the code compiles and runs without error — it just silently produces a wrong ordering.
