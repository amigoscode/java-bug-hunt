# Bug 73 -- Stream.sorted with null elements

**Bug:** The `sortByGrade` method uses `Comparator.comparing(Student::grade)`, which internally uses `Comparator.naturalOrder()`. The natural-order comparator does not handle `null` values and throws a `NullPointerException` when comparing a null grade.

```java
// Buggy:
return students.stream()
        .sorted(Comparator.comparing(Student::grade))  // NPE on null grade
        .toList();
```

**Fix:** Use `Comparator.nullsLast` (or `Comparator.nullsFirst`) to specify where nulls should be placed:

```java
// Fixed:
return students.stream()
        .sorted(Comparator.comparing(
                Student::grade,
                Comparator.nullsLast(Comparator.naturalOrder())
        ))
        .toList();
```

**Lesson:** `Comparator.comparing` with a single argument assumes the extracted keys are never `null`. When dealing with nullable fields, always use the two-argument overload and wrap the secondary comparator with `Comparator.nullsLast()` or `Comparator.nullsFirst()` to define null placement explicitly.
