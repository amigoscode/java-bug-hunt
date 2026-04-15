# Bug 07 — Wrong loop start index

**Bug:** Loop starts at `i = input.length()`, but valid character indices run from `0` to `length() - 1`. `charAt(length())` throws `StringIndexOutOfBoundsException`.

**Fix:** `for (int i = input.length() - 1; i >= 0; i--)`

**Lesson:** `length()` is a count, not an index. The last valid index is always `length() - 1`.
