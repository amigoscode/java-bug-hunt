# Bug 32 — Mutable field used in `hashCode`

**Bug:** `hashCode` includes `done`. When the item is added to the `HashSet`, it lands in a bucket based on its current hash. Calling `markDone()` changes `done`, so its hash changes — but the item is still in the *old* bucket. `contains()` looks in the *new* bucket and finds nothing.

**Fix:** Only include *immutable* fields in `hashCode` (and ideally in `equals`):
```java
@Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Item other)) return false;
    return Objects.equals(title, other.title);
}

@Override public int hashCode() {
    return Objects.hash(title);
}
```

**Lesson:** An object's hash **must not change** once it's inside a hash-based collection. Either make the fields immutable or keep mutable state out of `equals`/`hashCode`.
