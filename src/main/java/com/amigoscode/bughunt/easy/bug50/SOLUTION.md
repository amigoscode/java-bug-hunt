# Bug 50 — `==` on arrays compares references

**Bug:** Arrays don't override `equals`, and `==` compares references. Two distinct `byte[]` objects with identical contents always return `false`.

**Fix:** Use `Arrays.equals`:
```java
return java.util.Arrays.equals(this.bytes, other.bytes);
```

For cryptographic/security contexts (comparing hashes, MACs, signatures), use `MessageDigest.isEqual` or a constant-time comparator to avoid timing attacks:
```java
return java.security.MessageDigest.isEqual(this.bytes, other.bytes);
```

**Lesson:** `Arrays.equals` compares element-by-element. `Arrays.deepEquals` handles nested arrays. Never use `==` on arrays or `List`s of arrays. For security-sensitive data, `MessageDigest.isEqual` is the constant-time (timing-safe) option.
