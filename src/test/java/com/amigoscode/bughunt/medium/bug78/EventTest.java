package com.amigoscode.bughunt.medium.bug78;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    void attendeesListShouldNotBeModifiableThroughAccessor() {
        Event event = new Event("Standup",
                LocalDate.of(2026, 1, 15),
                new ArrayList<>(List.of("Alice", "Bob")));

        // Get the attendees and try to add someone
        List<String> attendees = event.attendees();
        try {
            attendees.add("Hacker");
        } catch (UnsupportedOperationException e) {
            // This is the correct behaviour -- list should be unmodifiable
            return;
        }

        // If we reach here, the add succeeded -- the record is NOT immutable.
        // The original record should NOT have been affected.
        assertThat(event.attendeeCount())
                .as("Record's attendee count should still be 2 after external modification")
                .isEqualTo(2);
    }

    @Test
    void constructorShouldDefensivelyCopyAttendeesList() {
        List<String> original = new ArrayList<>(List.of("Alice"));
        Event event = new Event("Meeting", LocalDate.of(2026, 3, 10), original);

        // Modify the original list after construction
        original.add("Intruder");

        // The event should not see the modification
        assertThat(event.attendeeCount())
                .as("Event should not be affected by changes to the original list")
                .isEqualTo(1);
    }

    @Test
    void isAttendingShouldReturnTrueForRegisteredPerson() {
        Event event = new Event("Retro",
                LocalDate.of(2026, 2, 20),
                new ArrayList<>(List.of("Carol", "Dave")));

        assertThat(event.isAttending("Carol")).isTrue();
    }

    @Test
    void summaryShouldIncludeEventDetails() {
        Event event = new Event("Demo",
                LocalDate.of(2026, 6, 1),
                new ArrayList<>(List.of("Eve")));

        assertThat(event.summary()).contains("Demo").contains("1 attendee");
    }

    @Test
    void mergeAttendeesShouldCombineWithoutDuplicates() {
        Event a = new Event("A", LocalDate.of(2026, 1, 1),
                new ArrayList<>(List.of("Alice", "Bob")));
        Event b = new Event("B", LocalDate.of(2026, 1, 2),
                new ArrayList<>(List.of("Bob", "Carol")));

        Event merged = a.mergeAttendees(b);

        assertThat(merged.attendees()).containsExactly("Alice", "Bob", "Carol");
    }
}
