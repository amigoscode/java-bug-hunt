package com.amigoscode.bughunt.medium.bug86;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A notification queue that starts with a default notification and allows
 * additional notifications to be enqueued, dequeued, and inspected.
 *
 * <p>BUG: The constructor initialises the internal list with
 * {@link Collections#singletonList(Object)}, which returns an immutable
 * single-element list. Any attempt to add or remove elements throws
 * {@link UnsupportedOperationException}.
 */
public class NotificationQueue {

    private final List<String> notifications;

    /**
     * Creates a new queue pre-loaded with a default notification.
     *
     * @param defaultNotification the initial notification message
     */
    public NotificationQueue(String defaultNotification) {
        Objects.requireNonNull(defaultNotification, "defaultNotification must not be null");
        // BUG: Collections.singletonList returns an immutable list
        this.notifications = Collections.singletonList(defaultNotification);
    }

    /**
     * Adds a notification to the end of the queue.
     *
     * @param message the notification message
     * @throws UnsupportedOperationException because the backing list is immutable
     */
    public void enqueue(String message) {
        Objects.requireNonNull(message, "message must not be null");
        notifications.add(message);
    }

    /**
     * Removes and returns the first notification in the queue.
     *
     * @return the oldest notification
     * @throws IllegalStateException if the queue is empty
     * @throws UnsupportedOperationException because the backing list is immutable
     */
    public String dequeue() {
        if (notifications.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return notifications.remove(0);
    }

    /**
     * Returns the first notification without removing it.
     *
     * @return the oldest notification
     * @throws IllegalStateException if the queue is empty
     */
    public String peek() {
        if (notifications.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return notifications.get(0);
    }

    /**
     * Returns the current number of notifications in the queue.
     *
     * @return the queue size
     */
    public int size() {
        return notifications.size();
    }

    /**
     * Checks whether the queue contains a notification with the given message.
     *
     * @param message the message to search for
     * @return true if found
     */
    public boolean contains(String message) {
        Objects.requireNonNull(message, "message must not be null");
        return notifications.contains(message);
    }

    @Override
    public String toString() {
        return "NotificationQueue{size=" + notifications.size()
                + ", notifications=" + notifications + "}";
    }
}
