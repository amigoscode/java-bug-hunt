package com.amigoscode.bughunt.medium.bug81;

import java.util.Objects;

/**
 * Demonstrates the interface default-method diamond problem.
 * When a class implements two interfaces that each define the same
 * default method, the class must provide its own override to resolve
 * the ambiguity.
 *
 * BUG: The overridden {@code tag()} method only delegates to
 * {@code Loggable.super.tag()}, ignoring the {@code Auditable}
 * interface entirely. The expected behaviour is to combine both tags.
 */
public class AuditedService implements Loggable, Auditable {

    private final String name;

    /**
     * Creates a new AuditedService with the given logical name.
     *
     * @param name the service name used in tags
     */
    public AuditedService(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    /**
     * Returns the service name.
     */
    public String name() {
        return name;
    }

    /**
     * Produces a combined tag that should include both the LOG and AUDIT
     * prefixes so that downstream consumers can identify the source.
     *
     * <p>Expected format: {@code "LOG:<name>|AUDIT:<name>"}
     *
     * <p>BUG: only the Loggable tag is returned -- the Auditable tag is
     * completely missing from the output.
     *
     * @return the combined tag string
     */
    @Override
    public String tag() {
        // BUG: should combine both, e.g. Loggable.super.tag() + "|" + Auditable.super.tag()
        return Loggable.super.tag();
    }

    /**
     * Formats a log line using the current tag.
     *
     * @param message the message to log
     * @return a formatted log line
     */
    public String logLine(String message) {
        Objects.requireNonNull(message, "message must not be null");
        return "[" + tag() + "] " + message;
    }

    /**
     * Formats an audit entry using the current tag with a severity level.
     *
     * @param level   the severity level (e.g. INFO, WARN)
     * @param message the audit message
     * @return a formatted audit entry
     */
    public String auditEntry(String level, String message) {
        Objects.requireNonNull(level, "level must not be null");
        Objects.requireNonNull(message, "message must not be null");
        return tag() + " [" + level.toUpperCase() + "] " + message;
    }

    @Override
    public String toString() {
        return "AuditedService{name='" + name + "', tag='" + tag() + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditedService that)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
