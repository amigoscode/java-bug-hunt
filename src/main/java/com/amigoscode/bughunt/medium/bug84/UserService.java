package com.amigoscode.bughunt.medium.bug84;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A service that looks up users by ID, falling back to a default user
 * when no match is found.
 *
 * <p>BUG: {@link #findOrCreate(long)} uses {@link Optional#orElse(Object)}
 * instead of {@link Optional#orElseGet(java.util.function.Supplier)}.
 * {@code orElse} eagerly evaluates its argument, so {@code createDefault()}
 * is always called -- even when the user is found -- causing unwanted
 * side effects (the {@code defaultsCreated} counter increments every time).
 */
public class UserService {

    /**
     * A simple user record.
     */
    public record User(long id, String name) {
        public User {
            Objects.requireNonNull(name, "name must not be null");
        }
    }

    private final Map<Long, User> store = new HashMap<>();
    private int defaultsCreated = 0;

    /**
     * Seeds the service with a known user.
     *
     * @param user the user to store
     */
    public void register(User user) {
        Objects.requireNonNull(user, "user must not be null");
        store.put(user.id(), user);
    }

    /**
     * Looks up a user by ID.
     *
     * @param id the user ID
     * @return an Optional containing the user, or empty if not found
     */
    public Optional<User> findUser(long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Returns the user with the given ID, or creates and returns a default
     * user if no match exists.
     *
     * <p>BUG: uses {@code orElse(createDefault())} which always calls
     * {@code createDefault()}, even when the user is present. The lazy
     * alternative {@code orElseGet(() -> createDefault())} should be used
     * instead.
     *
     * @param id the user ID to look up
     * @return the found user or a new default user
     */
    public User findOrCreate(long id) {
        // BUG: createDefault() is always invoked (eager evaluation)
        return findUser(id).orElse(createDefault());
    }

    /**
     * Creates a default user with id 0 and name "default".
     * This method has a side effect: it increments {@code defaultsCreated}.
     *
     * @return a new default user
     */
    private User createDefault() {
        defaultsCreated++;
        return new User(0, "default");
    }

    /**
     * Returns the number of times a default user has been created.
     * Useful for monitoring unnecessary object creation.
     *
     * @return the count of defaults created
     */
    public int defaultsCreated() {
        return defaultsCreated;
    }

    /**
     * Returns the number of registered users.
     */
    public int size() {
        return store.size();
    }

    @Override
    public String toString() {
        return "UserService{users=" + store.size()
                + ", defaultsCreated=" + defaultsCreated + "}";
    }
}
