# Bug 03 — `==` vs `.equals()` on Strings

**Bug:** `normalized == "ADMIN"` compares references, not content. `trim()` + `toUpperCase()` produce a new String not interned with the literal.

**Fix:** `return "ADMIN".equals(normalized);`

**Lesson:** Use `.equals()` for Strings (and all objects). `==` only compares references. Put the literal on the left to avoid NPE on the variable.
