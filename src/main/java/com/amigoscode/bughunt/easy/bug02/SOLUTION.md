# Bug 02 — Off-by-one in loop

**Bug:** Loop uses `i <= numbers.length` which reads one past the end → `ArrayIndexOutOfBoundsException`.

**Fix:** `for (int i = 0; i < numbers.length; i++)`

**Lesson:** Valid array indices are `0` to `length - 1`. Prefer enhanced for-loop when possible: `for (int n : numbers)`.
