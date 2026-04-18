package com.amigoscode.bughunt.medium.bug57;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A map wrapper that stores heterogeneous values keyed by String,
 * with a typed retrieval method that should guarantee type safety.
 *
 * Use case: configuration stores, attribute maps, deserialized JSON bags.
 */
public class TypeSafeMap {

    private final Map<String, Object> store;

    public TypeSafeMap() {
        this.store = new HashMap<>();
    }

    /**
     * Stores a value under the given key.
     */
    public void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        store.put(key, value);
    }

    /**
     * Retrieves the value associated with the key, cast to the requested type.
     * Should throw ClassCastException immediately if the stored value
     * is not compatible with the requested type.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = store.get(key);
        if (value == null) {
            return null;
        }
        // BUG: unchecked cast — ignores the 'type' parameter entirely.
        // The cast to (T) is erased at runtime, so no ClassCastException
        // is thrown here. The caller only gets a CCE when they try to
        // use the returned value as the expected type.
        return (T) value;
    }

    /**
     * Returns the value as an Optional of the requested type.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptional(String key, Class<T> type) {
        Object value = store.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of((T) value);
    }

    /**
     * Checks whether the map contains a non-null value for the given key.
     */
    public boolean containsKey(String key) {
        return store.containsKey(key) && store.get(key) != null;
    }

    /**
     * Returns all keys currently in the map.
     */
    public Set<String> keys() {
        return Set.copyOf(store.keySet());
    }

    /**
     * Returns the number of entries in the map.
     */
    public int size() {
        return store.size();
    }

    /**
     * Removes a key from the map and returns the old value.
     */
    public Object remove(String key) {
        return store.remove(key);
    }

    /**
     * Merges another TypeSafeMap into this one. Existing keys are overwritten.
     */
    public void merge(TypeSafeMap other) {
        if (other == null) {
            throw new IllegalArgumentException("cannot merge null map");
        }
        for (String key : other.keys()) {
            this.store.put(key, other.get(key, Object.class));
        }
    }

    @Override
    public String toString() {
        return "TypeSafeMap" + store;
    }
}
