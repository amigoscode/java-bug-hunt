package com.amigoscode.bughunt.medium.bug78;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Represents a calendar event with a name, date, and list of attendees.
 * <p>
 * Declared as a {@code record} to signal immutability, but the attendees
 * list is a mutable {@link java.util.ArrayList} -- callers who obtain
 * the list via the accessor can modify it, breaking the immutability
 * guarantee.
 * <p>
 * BUG: The compact constructor does not create a defensive copy of the
 * attendees list. The accessor returns a direct reference to the mutable
 * list stored inside the record.
 *
 * @param name      the event name
 * @param date      the date of the event
 * @param attendees the list of attendee names
 */
public record Event(String name, LocalDate date, List<String> attendees) {

    /**
     * Compact constructor -- validates inputs but does NOT defensively copy
     * the mutable attendees list.
     */
    public Event {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(attendees, "attendees must not be null");
        // BUG: missing defensive copy -- attendees = List.copyOf(attendees);
    }

    /**
     * Returns the number of attendees.
     */
    public int attendeeCount() {
        return attendees.size();
    }

    /**
     * Checks whether the given person is attending the event.
     *
     * @param person the name to check
     * @return true if the person is in the attendees list
     */
    public boolean isAttending(String person) {
        Objects.requireNonNull(person, "person must not be null");
        return attendees.contains(person);
    }

    /**
     * Returns a formatted summary of the event, including the name,
     * date, and attendee count.
     *
     * @return a human-readable summary string
     */
    public String summary() {
        return String.format("Event '%s' on %s with %d attendee(s)",
                name, date, attendeeCount());
    }

    /**
     * Creates a new Event that merges the attendees of this event with
     * another event's attendees (no duplicates). The new event keeps
     * this event's name and date.
     *
     * @param other the other event whose attendees should be merged
     * @return a new Event with combined attendees
     */
    public Event mergeAttendees(Event other) {
        Objects.requireNonNull(other, "other must not be null");
        var merged = new java.util.ArrayList<>(attendees);
        for (String person : other.attendees()) {
            if (!merged.contains(person)) {
                merged.add(person);
            }
        }
        return new Event(name, date, merged);
    }

    @Override
    public String toString() {
        return summary();
    }
}
