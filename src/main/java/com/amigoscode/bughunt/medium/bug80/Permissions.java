package com.amigoscode.bughunt.medium.bug80;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manages a set of key-value permission entries.
 * Permissions are loaded from a stream of {@link Entry} records and can
 * be queried, granted, or revoked at runtime.
 *
 * BUG: The initial permissions are collected into an unmodifiable map
 * via {@link Collectors#toUnmodifiableMap}. Any subsequent call to
 * {@link #grant(String, String)} or {@link #revoke(String)} throws
 * {@link UnsupportedOperationException} because the underlying map
 * does not support mutation.
 */
public class Permissions {

    /**
     * A simple key-value pair representing a single permission entry.
     */
    public record Entry(String key, String value) {
        public Entry {
            Objects.requireNonNull(key, "key must not be null");
            Objects.requireNonNull(value, "value must not be null");
        }
    }

    private final Map<String, String> perms;

    /**
     * Creates a Permissions instance by loading entries from the given stream.
     * <p>
     * BUG: collects into an unmodifiable map -- later mutations throw.
     *
     * @param entries the stream of permission entries to load
     */
    public Permissions(Stream<Entry> entries) {
        Objects.requireNonNull(entries, "entries must not be null");
        // BUG: toUnmodifiableMap produces an immutable map
        this.perms = entries.collect(
                Collectors.toUnmodifiableMap(Entry::key, Entry::value));
    }

    /**
     * Looks up the value associated with the given permission key.
     *
     * @param key the permission key
     * @return an Optional containing the value, or empty if not found
     */
    public Optional<String> get(String key) {
        Objects.requireNonNull(key, "key must not be null");
        return Optional.ofNullable(perms.get(key));
    }

    /**
     * Checks whether a permission with the given key exists.
     *
     * @param key the permission key
     * @return true if the key is present
     */
    public boolean has(String key) {
        Objects.requireNonNull(key, "key must not be null");
        return perms.containsKey(key);
    }

    /**
     * Grants a new permission or updates an existing one.
     * <p>
     * BUG: throws UnsupportedOperationException because the map is unmodifiable.
     *
     * @param key   the permission key
     * @param value the permission value
     */
    public void grant(String key, String value) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(value, "value must not be null");
        perms.put(key, value); // throws UnsupportedOperationException
    }

    /**
     * Revokes (removes) a permission by key.
     * <p>
     * BUG: throws UnsupportedOperationException because the map is unmodifiable.
     *
     * @param key the permission key to revoke
     * @return true if the permission was present and removed
     */
    public boolean revoke(String key) {
        Objects.requireNonNull(key, "key must not be null");
        return perms.remove(key) != null; // throws UnsupportedOperationException
    }

    /**
     * Returns the total number of permissions currently held.
     */
    public int size() {
        return perms.size();
    }

    @Override
    public String toString() {
        return "Permissions{count=" + perms.size() + ", keys=" + perms.keySet() + "}";
    }
}
