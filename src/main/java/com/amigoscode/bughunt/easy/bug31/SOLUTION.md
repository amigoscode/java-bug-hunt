# Bug 31 — Exposing internal mutable state

**Bug:** `start()` returns the field directly. Callers can mutate the returned `Date` and silently corrupt the appointment.

**Fix — defensive copy:**
```java
public Date start() {
    return new Date(start.getTime());
}
```
Even better — defensive copy in the constructor too, otherwise the *caller* can mutate the Date they passed in:
```java
public Appointment(String subject, Date start, int durationMinutes) {
    this.start = new Date(start.getTime());
    ...
}
```

**Best:** avoid `Date` entirely — use `java.time.Instant` or `LocalDateTime`. They're *immutable* and eliminate this class of bug by construction.

**Lesson:** A class with mutable internals must either (a) defensively copy on the way in and out, or (b) use immutable types. "Privacy" of a field isn't privacy if you hand out the reference.
