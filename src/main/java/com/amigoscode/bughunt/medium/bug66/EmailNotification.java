package com.amigoscode.bughunt.medium.bug66;

import java.util.Objects;

/**
 * An email notification that includes a recipient address.
 * Overrides toString() to include the recipient in the log representation.
 */
public class EmailNotification extends Notification {

    private final String recipient;
    private final String subject;

    /**
     * Creates an email notification.
     *
     * @param message   the notification body
     * @param recipient the email recipient address
     * @param subject   the email subject line
     */
    public EmailNotification(String message, String recipient, String subject) {
        super(message); // BUG: super constructor calls toString(), but recipient is still null here
        this.recipient = Objects.requireNonNull(recipient, "recipient must not be null");
        this.subject = Objects.requireNonNull(subject, "subject must not be null");
    }

    /**
     * Convenience constructor with a default subject.
     */
    public EmailNotification(String message, String recipient) {
        this(message, recipient, "No Subject");
    }

    /**
     * Returns the recipient email address.
     */
    public String recipient() {
        return recipient;
    }

    /**
     * Returns the subject line.
     */
    public String subject() {
        return subject;
    }

    /**
     * Builds the full email header string.
     */
    public String emailHeader() {
        return "To: " + recipient + " | Subject: " + subject;
    }

    /**
     * Checks whether this notification targets the given domain.
     *
     * @param domain the domain to check (e.g. "test.com")
     * @return true if the recipient's address ends with the given domain
     */
    public boolean isForDomain(String domain) {
        return recipient.endsWith("@" + domain) || recipient.endsWith("." + domain);
    }

    /**
     * Returns a formatted representation including recipient and message.
     * This override is dispatched by the superclass constructor before
     * recipient has been assigned, causing it to include "null".
     */
    @Override
    public String toString() {
        return "EmailNotification{to='" + recipient + "', message='" + message() + "'}";
    }
}
