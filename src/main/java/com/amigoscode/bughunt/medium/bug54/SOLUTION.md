# Bug 54 — Equals symmetry broken across inheritance

**Bug:** `Employee.equals` uses `instanceof`, so an `Employee` considers a `Manager` with the same ID as equal. But `Manager.equals` uses `getClass()`, requiring the other object to also be a `Manager` — so a `Manager` does not consider a plain `Employee` as equal. This breaks the symmetry contract: `employee.equals(manager)` returns `true` while `manager.equals(employee)` returns `false`.

**Fix:** Use the same strategy in both classes. The simplest correct approach is to use `getClass()` in both:
```java
// In Employee
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee other = (Employee) o;
    return Objects.equals(id, other.id);
}
```

Or, if cross-type equality by ID is desired, use `instanceof` in both and do not add extra fields to the subclass comparison.

**Lesson:** Mixing `instanceof` and `getClass()` in an inheritance hierarchy is a classic way to break the `equals` symmetry contract. Pick one strategy and apply it consistently. Josh Bloch's *Effective Java* Item 10 covers this in depth.
