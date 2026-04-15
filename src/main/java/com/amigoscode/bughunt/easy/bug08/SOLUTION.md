# Bug 08 — Array index out of bounds

**Bug:** `arr[arr.length]` is past the end. Arrays are zero-indexed.

**Fix:** `return arr[arr.length - 1];`

**Lesson:** Same root cause as the loop bug — array length is a count, not an index.
