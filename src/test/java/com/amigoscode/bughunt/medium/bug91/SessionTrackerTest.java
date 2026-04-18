package com.amigoscode.bughunt.medium.bug91;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTrackerTest {

    @Test
    void isSameSessionShouldReturnTrueForEqualLargeIds() {
        SessionTracker tracker = new SessionTracker();

        // Two separate Long objects with the same value > 127 (outside cache range)
        Long id1 = Long.valueOf(1000L);
        Long id2 = Long.valueOf(1000L);

        tracker.register(id1, "alice");

        // These have the same value, so isSameSession should return true
        assertThat(tracker.isSameSession(id1, id2)).isTrue();
    }

    @Test
    void isSameSessionShouldWorkForGeneratedIds() {
        SessionTracker tracker = new SessionTracker();

        // Simulate IDs generated from a sequence or database
        long rawId = 500_000L;
        Long sessionA = Long.valueOf(rawId);
        Long sessionB = Long.valueOf(rawId);

        tracker.register(sessionA, "bob");

        assertThat(tracker.isSameSession(sessionA, sessionB)).isTrue();
    }

    @Test
    void registerAndLookUpSession() {
        SessionTracker tracker = new SessionTracker();

        tracker.register(42L, "carol");

        assertThat(tracker.getUser(42L)).isEqualTo("carol");
        assertThat(tracker.activeCount()).isEqualTo(1);
    }

    @Test
    void endSessionShouldRemoveIt() {
        SessionTracker tracker = new SessionTracker();

        tracker.register(99L, "dave");

        assertThat(tracker.endSession(99L)).isTrue();
        assertThat(tracker.activeCount()).isEqualTo(0);
    }

    @Test
    void historyShouldTrackAllRegistrations() {
        SessionTracker tracker = new SessionTracker();

        tracker.register(1L, "eve");
        tracker.register(2L, "frank");
        tracker.register(3L, "grace");

        assertThat(tracker.history()).containsExactly(1L, 2L, 3L);
    }
}
