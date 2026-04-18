# Bug 58 — Array covariance causes ArrayStoreException

**Bug:** Java arrays are covariant: `Square[]` can be assigned to a `Shape[]` variable. The compiler allows `shapes[0] = new Circle(...)` because the declared type is `Shape[]`. But at runtime, the JVM knows the array is actually a `Square[]` and throws `ArrayStoreException` when you try to store a `Circle` in it.

**Fix:** Use a `List<Shape>` instead of `Shape[]`. Generics are invariant (`List<Square>` is NOT a `List<Shape>`), so the compiler catches this at compile time:
```java
public void replaceFirst(List<Shape> shapes, Shape replacement) {
    shapes.set(0, replacement);
}
```

Alternatively, if you must use arrays, create a new `Shape[]` instead of accepting a subtype array:
```java
public Shape[] replaceFirst(Shape[] shapes, Shape replacement) {
    Shape[] result = new Shape[shapes.length];
    System.arraycopy(shapes, 0, result, 0, shapes.length);
    result[0] = replacement;
    return result;
}
```

**Lesson:** Array covariance is a well-known design flaw in Java's type system. It was introduced before generics existed to allow methods like `Arrays.sort(Object[])` to work polymorphically. Prefer `List<T>` over `T[]` for method parameters — generics are invariant by design and catch these errors at compile time.
