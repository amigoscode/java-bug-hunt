# Bug 61 — Resource not closed when processing throws

**Bug:** The `process` method calls `resource.close()` after `worker.execute(resource)`, but if `execute` throws an exception, control flow jumps out of the method and `close()` is never reached. This leaks the resource (file handle, connection, etc.).

**Fix:** Use try-with-resources or a try-finally block to guarantee the resource is closed regardless of whether processing succeeds or fails:

```java
public String process(ResourceFactory factory, ResourceWorker worker) throws Exception {
    auditLog.add("OPEN");
    Closeable resource = factory.open();
    try {
        String result = worker.execute(resource);
        return result;
    } finally {
        resource.close();
        auditLog.add("CLOSE");
    }
}
```

Or with try-with-resources:

```java
public String process(ResourceFactory factory, ResourceWorker worker) throws Exception {
    auditLog.add("OPEN");
    try (Closeable resource = factory.open()) {
        String result = worker.execute(resource);
        auditLog.add("CLOSE");
        return result;
    }
}
```

**Lesson:** Always use try-with-resources or try-finally to close resources. Placing `close()` after the code that uses the resource means any exception skips cleanup entirely, leading to resource leaks that are hard to diagnose in production.
