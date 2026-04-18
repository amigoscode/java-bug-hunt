# Bug 81 -- Interface default method diamond: only one super called

**Bug:** `AuditedService` implements both `Loggable` and `Auditable`, which each define a `default String tag()` method. The class resolves the diamond by overriding `tag()`, but it only calls `Loggable.super.tag()` -- completely ignoring `Auditable.super.tag()`. The AUDIT prefix never appears in the output.

```java
// Buggy:
@Override
public String tag() {
    return Loggable.super.tag();  // only LOG:name -- AUDIT is lost
}
```

**Fix:** Combine both interface tags:

```java
// Fixed:
@Override
public String tag() {
    return Loggable.super.tag() + "|" + Auditable.super.tag();
}
```

**Lesson:** When a class implements multiple interfaces that define the same default method, Java requires an explicit override to resolve the ambiguity. It is easy to delegate to just one interface via `InterfaceName.super.method()` and forget the other. Always check that the override honours the contract of every interface the class implements. The compiler forces you to resolve the conflict, but it cannot verify that your resolution is semantically correct.
