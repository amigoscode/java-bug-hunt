# Bug 63 — Finally block exception masks original exception

**Bug:** In the `execute` method, if `process()` throws an exception and then `cleanup()` in the `finally` block also throws, Java discards the original exception from `process()` and propagates the one from `cleanup()`. The caller never sees the real cause of failure (e.g., "data corrupted"), only the cleanup error (e.g., "temp files locked").

**Fix:** Catch the cleanup exception and add it as a suppressed exception on the original:

```java
public String execute(BatchJob job) throws Exception {
    job.initialize();
    executionLog.add("INITIALIZED");

    Exception primaryException = null;
    try {
        String result = job.process();
        executionLog.add("PROCESSED");
        return result;
    } catch (Exception e) {
        primaryException = e;
        throw e;
    } finally {
        try {
            job.cleanup();
            executionLog.add("CLEANED_UP");
        } catch (Exception cleanupEx) {
            executionLog.add("CLEANED_UP");
            if (primaryException != null) {
                primaryException.addSuppressed(cleanupEx);
            } else {
                throw cleanupEx;
            }
        }
    }
}
```

**Lesson:** When a `finally` block can throw, it risks masking the original exception. This is exactly the problem `try-with-resources` was designed to solve — it automatically adds close-time exceptions as suppressed exceptions. When you cannot use try-with-resources, manually catch exceptions in `finally` and attach them via `addSuppressed()`.
