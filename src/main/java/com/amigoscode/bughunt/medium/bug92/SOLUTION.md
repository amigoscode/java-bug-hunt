# Bug 92 -- StringBuilder capacity vs length

**Bug:** The `BufferWrapper` constructor calls `new StringBuilder(size)` assuming this creates a buffer pre-filled with `size` characters. In reality, `StringBuilder(int)` sets the internal *capacity* (pre-allocated storage) but the *length* (number of characters) remains 0. Calling `charAt(0)` on an empty `StringBuilder` throws `StringIndexOutOfBoundsException`.

```java
// Buggy:
this.buffer = new StringBuilder(size); // capacity = size, length = 0!
buffer.charAt(0); // throws StringIndexOutOfBoundsException
```

**Fix:** If you want a buffer pre-filled with characters, explicitly set the length or fill it:

```java
// Option 1: use setLength to fill with null chars
this.buffer = new StringBuilder(size);
this.buffer.setLength(size);

// Option 2: fill with a specific character
this.buffer = new StringBuilder(size);
for (int i = 0; i < size; i++) {
    this.buffer.append('\0');
}

// Option 3: use String constructor
this.buffer = new StringBuilder("\0".repeat(size));
```

**Lesson:** `StringBuilder(int)` and `new ArrayList<>(int)` are capacity hints for performance -- they do NOT create elements. The same trap applies to `StringBuffer(int)`. Always check the Javadoc: capacity is about pre-allocating memory, length/size is about actual content.
