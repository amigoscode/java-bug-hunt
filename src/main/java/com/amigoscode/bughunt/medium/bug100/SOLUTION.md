# Bug 100 -- String.format %s on array prints garbage

**Bug:** `String.format("Items: %s", items)` where `items` is a `String[]` calls `Object.toString()` on the array. Java arrays do not override `toString()`, so the result is the default representation like `[Ljava.lang.String;@1a2b3c4d` instead of a readable list of elements. The same applies to `int[]` producing `[I@hash`.

```java
// Buggy:
return String.format("Items: %s", items);
// Output: "Items: [Ljava.lang.String;@5e91993f"
```

**Fix:** Use `Arrays.toString()` to convert the array to a readable string before formatting:

```java
// Fixed:
return String.format("Items: %s", Arrays.toString(items));
// Output: "Items: [cpu, memory, disk]"
```

**Lesson:** Java arrays inherit `toString()` from `Object`, which prints the type and hash code, not the contents. Always use `Arrays.toString()` for one-dimensional arrays or `Arrays.deepToString()` for multi-dimensional arrays when you need a human-readable representation. This is one of the most common formatting traps in Java because arrays look like they should "just work" with `%s`, but they do not.
