# Bug 25 — `equals` with the wrong parameter type

**Bug:** `equals(Money other)` is an *overload*, not an override. The real `Object.equals(Object)` is never overridden, so `HashSet`, AssertJ's `isEqualTo`, and any framework that calls `a.equals((Object) b)` fall back to reference equality — always `false` for distinct instances.

**Fix:** Override the real method:
```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Money other)) return false;
    return cents == other.cents && Objects.equals(currency, other.currency);
}
```

**Lesson:** Always add `@Override`. If the annotation doesn't compile, you're *not* overriding — you're overloading. This single annotation prevents a huge class of silent bugs.
