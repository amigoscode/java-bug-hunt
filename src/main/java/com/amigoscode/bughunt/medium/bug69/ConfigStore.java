package com.amigoscode.bughunt.medium.bug69;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple configuration store that holds string entries.
 * Provides a "snapshot" method intended to return an immutable view
 * of the current configuration state.
 */
public class ConfigStore {

    private final List<String> entries = new ArrayList<>();
    private final String storeName;

    /**
     * Creates a new config store with the given name.
     *
     * @param storeName the name of this configuration store
     */
    public ConfigStore(String storeName) {
        this.storeName = Objects.requireNonNull(storeName, "storeName must not be null");
    }

    /**
     * Adds a configuration entry to the store.
     *
     * @param entry the entry to add
     */
    public void addEntry(String entry) {
        Objects.requireNonNull(entry, "entry must not be null");
        entries.add(entry);
    }

    /**
     * Adds multiple configuration entries at once.
     *
     * @param newEntries the entries to add
     */
    public void addAll(List<String> newEntries) {
        for (String entry : newEntries) {
            addEntry(entry);
        }
    }

    /**
     * Returns a snapshot of the current configuration entries.
     * The returned list is unmodifiable to prevent external mutations.
     *
     * BUG: Collections.unmodifiableList wraps the LIVE internal list,
     * not a copy. Changes to the internal list after this call will
     * be visible through the returned "snapshot".
     *
     * @return an unmodifiable view of the entries
     */
    public List<String> snapshot() {
        return Collections.unmodifiableList(entries); // BUG: view, not a copy
    }

    /**
     * Returns the number of entries currently in the store.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Checks whether the store contains the given entry.
     *
     * @param entry the entry to look for
     * @return true if the entry exists
     */
    public boolean contains(String entry) {
        return entries.contains(entry);
    }

    /**
     * Returns the store name.
     */
    public String storeName() {
        return storeName;
    }

    /**
     * Removes all entries from the store.
     */
    public void clear() {
        entries.clear();
    }

    @Override
    public String toString() {
        return "ConfigStore{name='" + storeName + "', entries=" + entries.size() + "}";
    }
}
