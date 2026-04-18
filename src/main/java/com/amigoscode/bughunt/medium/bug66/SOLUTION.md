# Bug 66 -- Subclass toString() called from superclass constructor

**Bug:** The `Notification` base class calls `toString()` inside its constructor to capture a `logEntry`. The subclass `EmailNotification` overrides `toString()` to include the `recipient` field. However, the superclass constructor runs *before* the subclass assigns its fields, so `recipient` is still `null` when `toString()` is called. The `logEntry` ends up containing "null" instead of the actual recipient address.

```java
// In Notification constructor:
this.logEntry = toString(); // dispatches to EmailNotification.toString()

// In EmailNotification constructor:
super(message);            // toString() called here, recipient is null
this.recipient = recipient; // assigned AFTER super() returns
```

**Fix:** Do not call overridable methods from constructors. Instead, build the log entry lazily or pass all required data to the superclass constructor:

```java
// Option 1: Lazy log entry
public String logEntry() {
    return toString(); // called on demand, after full construction
}

// Option 2: Pass data explicitly
public Notification(String message, String logPrefix) {
    this.logEntry = logPrefix + message;
}
```

**Lesson:** Calling overridable methods from a constructor is a well-known anti-pattern in Java. The subclass override executes before the subclass constructor body, observing uninitialized fields. Effective Java Item 19 warns: "Do not invoke overridable methods from constructors."
