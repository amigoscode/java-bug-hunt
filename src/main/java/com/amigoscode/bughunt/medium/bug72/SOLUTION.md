# Bug 72 -- Collectors.toMap duplicate key

**Bug:** The `emailToNameMap` method uses the two-argument `Collectors.toMap(keyMapper, valueMapper)`. When two contacts share the same email address, this collector throws an `IllegalStateException: Duplicate key ...` because it has no strategy for merging duplicate keys.

```java
// Buggy:
return contacts.stream()
        .collect(Collectors.toMap(Contact::email, Contact::name));
// Throws IllegalStateException on duplicate emails
```

**Fix:** Use the three-argument overload that provides a merge function:

```java
// Keep the latest (last) contact's name:
return contacts.stream()
        .collect(Collectors.toMap(
                Contact::email,
                Contact::name,
                (existing, replacement) -> replacement
        ));

// Or keep the first contact's name:
return contacts.stream()
        .collect(Collectors.toMap(
                Contact::email,
                Contact::name,
                (existing, replacement) -> existing
        ));
```

**Lesson:** The default `Collectors.toMap` is strict about uniqueness -- it throws on duplicate keys rather than silently overwriting. This is a deliberate design choice to avoid data loss, but it means you must always consider whether your key mapper can produce duplicates. When duplicates are possible, use the three-argument form with an explicit merge function.
