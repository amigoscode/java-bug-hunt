# Bug 10 — String immutability

**Bug:** `input.trim()` and `input.toLowerCase()` return new Strings but the results are discarded. `input` itself never changes.

**Fix:** `return input.trim().toLowerCase();`

**Lesson:** Strings are immutable. Every String method returns a *new* String — you must capture or return it.
