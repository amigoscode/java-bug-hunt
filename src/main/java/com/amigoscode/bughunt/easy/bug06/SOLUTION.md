# Bug 06 — Operator precedence

**Bug:** `a + b + c / 3` is parsed as `a + b + (c / 3)` because `/` has higher precedence than `+`.

**Fix:** `return (a + b + c) / 3.0;`

**Lesson:** `*`, `/`, `%` bind tighter than `+`, `-`. Use parentheses to make intent explicit — never rely on the reader to remember the full precedence table.
