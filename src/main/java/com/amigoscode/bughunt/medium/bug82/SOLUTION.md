# Bug 82 -- Sealed class pattern matching missing Triangle case

**Bug:** The `area()` method uses `instanceof` pattern matching to dispatch on `Circle` and `Rectangle`, but it never checks for `Triangle`. Because the method ends with `return 0`, any triangle silently produces an area of zero instead of the correct value.

```java
// Buggy:
public double area(Shape shape) {
    if (shape instanceof Circle c) return Math.PI * c.radius() * c.radius();
    if (shape instanceof Rectangle r) return r.width() * r.height();
    return 0;  // Triangle ends up here!
}
```

**Fix:** Add the missing `Triangle` case:

```java
// Fixed:
public double area(Shape shape) {
    if (shape instanceof Circle c) return Math.PI * c.radius() * c.radius();
    if (shape instanceof Rectangle r) return r.width() * r.height();
    if (shape instanceof Triangle t) return 0.5 * t.base() * t.height();
    throw new IllegalArgumentException("Unknown shape: " + shape);
}
```

**Lesson:** Sealed types guarantee a closed set of subtypes, but `if`/`instanceof` chains do not get compile-time exhaustiveness checking (unlike `switch` expressions in newer Java versions). When you add a new permitted subtype to a sealed hierarchy, you must manually audit every dispatch site. Prefer `switch` expressions with pattern matching where the compiler can enforce exhaustiveness.
