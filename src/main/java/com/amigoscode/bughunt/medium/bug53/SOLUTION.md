# Bug 53 — TreeSet comparator drops elements with equal scores

**Bug:** The `TreeSet` uses `Comparator.comparingInt(Student::getScore).reversed()`. When two students have the same score, the comparator returns 0 — and `TreeSet` treats 0 as "same element", silently discarding the second student.

**Fix:** Add a tiebreaker to the comparator so that students with the same score are still distinguishable:
```java
this.rankings = new TreeSet<>(
    Comparator.comparingInt(Student::getScore).reversed()
              .thenComparing(Student::getId)
);
```

**Lesson:** A `TreeSet` (and `TreeMap`) uses the comparator for all equality decisions — not `equals()`. If your comparator returns 0 for distinct objects, the set will treat them as duplicates. Always include a tiebreaker field (like an ID) in your comparator to ensure uniqueness is preserved.
