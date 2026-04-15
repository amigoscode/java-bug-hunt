# Bug 09 — Wrong initial min/max

**Bug:** `min` and `max` both default to `0`. A first reading of `25` doesn't update `min` because `25 < 0` is false. All-positive or all-negative inputs give wrong results.

**Fix:** Initialise `min = Integer.MAX_VALUE`, `max = Integer.MIN_VALUE`. Or track whether any reading has been recorded.

**Lesson:** Default field values (`0`, `null`, `false`) are rarely the right sentinel for "no data yet". Pick an initial value that the first real input is guaranteed to replace.
