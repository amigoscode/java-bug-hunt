package com.amigoscode.bughunt.medium.bug66;

import java.time.Instant;
import java.util.Objects;

/**
 * Base class for notifications. Records a log entry at construction time
 * by calling toString(), which subclasses may override.
 */
public class Notification {

    private final String message;
    private final Instant createdAt;
    private final String logEntry;

    /**
     * Creates a new notification with the given message.
     * Captures a log entry snapshot via toString() at construction time.
     *
     * @param message the notification message
     */
    public Notification(String message) {
        this.message = Objects.requireNonNull(message, "message must not be null");
        this.createdAt = Instant.now();
        // BUG: toString() dispatches to the subclass override,
        // but subclass fields are not yet initialized at this point.
        this.logEntry = toString();
    }

    /**
     * Returns the notification message.
     */
    public String message() {
        return message;
    }

    /**
     * Returns the creation timestamp.
     */
    public Instant createdAt() {
        return createdAt;
    }

    /**
     * Returns the log entry that was captured at construction time.
     */
    public String logEntry() {
        return logEntry;
    }

    /**
     * Returns a formatted summary of this notification suitable for audit logs.
     */
    public String auditSummary() {
        return String.format("[%s] %s", createdAt, logEntry);
    }

    @Override
    public String toString() {
        return "Notification{message='" + message + "'}";
    }
}
