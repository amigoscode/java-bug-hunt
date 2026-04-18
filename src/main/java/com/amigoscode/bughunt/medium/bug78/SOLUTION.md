# Bug 78 -- Record with mutable field breaks immutability

**Bug:** The `Event` record stores its `attendees` list by reference. Records generate accessor methods that return the field directly -- they do not create defensive copies. A caller who obtains the list via `event.attendees()` receives a reference to the same mutable `ArrayList` stored inside the record. Any modifications to that list (adding, removing, clearing) mutate the record's internal state, violating the immutability contract.

Similarly, if the caller retains a reference to the `List` passed into the constructor and later modifies it, the record's internal state changes.

```java
// Buggy:
public record Event(String name, LocalDate date, List<String> attendees) {
    public Event {
        // validates but does NOT defensively copy
    }
}
```

**Fix:** Create a defensive (unmodifiable) copy in the compact constructor:

```java
public record Event(String name, LocalDate date, List<String> attendees) {
    public Event {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(attendees);
        attendees = List.copyOf(attendees);  // unmodifiable defensive copy
    }
}
```

**Lesson:** Java records provide shallow immutability only. The generated accessors return direct references to the fields. If a field's type is mutable (e.g. `List`, `Map`, `Date`, arrays), you must create a defensive copy in the compact constructor using `List.copyOf()`, `Map.copyOf()`, or similar. `List.copyOf()` also rejects null elements, providing extra safety.
