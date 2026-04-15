# Bug 13 — Assignment instead of comparison

**Bug:** `if (locked = true)` is an *assignment*, not a comparison. It sets `locked` to `true` and then evaluates to `true` — so every call enters the branch and the account can never be accessed.

**Fix:** `if (locked)` (or `if (locked == true)` — but plain `if (locked)` is idiomatic).

**Lesson:** `=` assigns, `==` compares. In Java this is only possible with booleans (`if (x = 5)` won't compile), which is why the bug slips through. Many linters (SpotBugs, IntelliJ inspections) flag this — enable them.
