# Bug 35 — Sorting an unmodifiable list

**Bug:** The test passes `List.of(...)` — an *immutable* list. The constructor stores that reference directly, so `Collections.sort` (which mutates the list) throws `UnsupportedOperationException`.

**Fix:** Defensively copy in the constructor:
```java
public Playlist(String name, List<Track> tracks) {
    this.name = name;
    this.tracks = new ArrayList<>(tracks);
}
```

**Lesson:** A class that intends to mutate a collection field must *own* the collection. Always copy on input (and don't return the field directly — that's Bug 31). The constructor boundary is where ownership transfers.
