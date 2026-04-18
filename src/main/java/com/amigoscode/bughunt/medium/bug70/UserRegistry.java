package com.amigoscode.bughunt.medium.bug70;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A registry that maps user IDs to display names.
 * Supports register-or-get semantics: if a user is already registered,
 * return their existing name; otherwise register and return the new name.
 */
public class UserRegistry {

    private final Map<Long, String> users = new HashMap<>();
    private final String registryName;

    /**
     * Creates a new user registry.
     *
     * @param registryName a name for this registry instance
     */
    public UserRegistry(String registryName) {
        this.registryName = Objects.requireNonNull(registryName, "registryName must not be null");
    }

    /**
     * Registers a user with the given ID and name, or returns the existing
     * name if the ID is already registered.
     *
     * BUG: putIfAbsent returns null when the key was NOT present (i.e., when
     * the new value was inserted). It returns the EXISTING value only when
     * the key was already present. The code assumes it returns the stored value
     * in both cases.
     *
     * @param id   the user ID
     * @param name the display name to register
     * @return the registered name (new or existing)
     */
    public String registerOrGet(long id, String name) {
        Objects.requireNonNull(name, "name must not be null");
        // BUG: returns null for newly inserted entries, not the name
        return users.putIfAbsent(id, name);
    }

    /**
     * Looks up a user by ID.
     *
     * @param id the user ID
     * @return an Optional containing the name, or empty if not found
     */
    public Optional<String> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Returns the total number of registered users.
     */
    public int userCount() {
        return users.size();
    }

    /**
     * Checks whether a user with the given ID is registered.
     *
     * @param id the user ID
     * @return true if the user is registered
     */
    public boolean isRegistered(long id) {
        return users.containsKey(id);
    }

    /**
     * Removes a user from the registry.
     *
     * @param id the user ID to remove
     * @return the name of the removed user, or null if not found
     */
    public String remove(long id) {
        return users.remove(id);
    }

    /**
     * Returns the registry name.
     */
    public String registryName() {
        return registryName;
    }

    @Override
    public String toString() {
        return "UserRegistry{name='" + registryName + "', users=" + users.size() + "}";
    }
}
