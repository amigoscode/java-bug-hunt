# Bug 14 — Empty catch block

**Bug:** The `catch` block silently swallows the exception. `errorCount` is never incremented and `invalidInputs` is never appended. Tests that verify error tracking fail.

**Fix:**
```java
} catch (NumberFormatException e) {
    errorCount++;
    invalidInputs.add(raw);
}
```

**Lesson:** Empty `catch` blocks hide problems. At minimum, log the exception. If you truly want to swallow it, name the variable `ignored` and add a comment explaining *why* — so reviewers know it was intentional.
