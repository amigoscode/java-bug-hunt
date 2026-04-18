package com.amigoscode.bughunt.medium.bug91;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Tracks active user sessions by their session ID.
 * Provides methods to register, look up, and compare sessions.
 *
 * Each session has a Long ID and an associated username.
 */
public class SessionTracker {

    private final Map<Long, String> activeSessions = new HashMap<>();
    private final List<Long> sessionHistory = new ArrayList<>();

    /**
     * Registers a new session for the given user and returns the session ID.
     *
     * @param sessionId the session ID to register
     * @param username  the username for this session
     * @throws IllegalArgumentException if sessionId is null or username is blank
     */
    public void register(Long sessionId, String username) {
        Objects.requireNonNull(sessionId, "sessionId must not be null");
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username must not be blank");
        }
        activeSessions.put(sessionId, username);
        sessionHistory.add(sessionId);
    }

    /**
     * Returns the username associated with the given session ID.
     *
     * @param sessionId the session ID to look up
     * @return the username, or null if not found
     */
    public String getUser(Long sessionId) {
        return activeSessions.get(sessionId);
    }

    /**
     * Checks whether two session IDs refer to the same session.
     *
     * BUG: Uses == to compare Long objects. This works for values in the
     * cached range (-128 to 127) because the JVM caches those Long instances,
     * but fails for values outside that range because two distinct Long
     * objects with the same value are not reference-equal.
     *
     * @param sessionId1 the first session ID
     * @param sessionId2 the second session ID
     * @return true if both IDs represent the same session
     */
    public boolean isSameSession(Long sessionId1, Long sessionId2) {
        // BUG: == compares references, not values, for Long objects > 127
        return sessionId1 == sessionId2;
    }

    /**
     * Removes a session from the active sessions map.
     *
     * @param sessionId the session ID to remove
     * @return true if the session was active and has been removed
     */
    public boolean endSession(Long sessionId) {
        return activeSessions.remove(sessionId) != null;
    }

    /**
     * Returns the number of currently active sessions.
     */
    public int activeCount() {
        return activeSessions.size();
    }

    /**
     * Returns an unmodifiable view of the session history (all IDs ever registered).
     */
    public List<Long> history() {
        return Collections.unmodifiableList(sessionHistory);
    }

    /**
     * Finds all session IDs belonging to a given username.
     *
     * @param username the username to search for
     * @return a list of matching session IDs
     */
    public List<Long> findSessionsByUser(String username) {
        List<Long> result = new ArrayList<>();
        for (Map.Entry<Long, String> entry : activeSessions.entrySet()) {
            if (entry.getValue().equals(username)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "SessionTracker{active=" + activeSessions.size()
                + ", history=" + sessionHistory.size() + "}";
    }
}
