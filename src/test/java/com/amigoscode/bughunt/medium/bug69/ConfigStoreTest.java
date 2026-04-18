package com.amigoscode.bughunt.medium.bug69;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigStoreTest {

    @Test
    void snapshotShouldNotChangeWhenStoreIsModified() {
        ConfigStore store = new ConfigStore("app-config");
        store.addEntry("db.host=localhost");
        store.addEntry("db.port=5432");

        // Take a snapshot of the current state
        List<String> snap = store.snapshot();
        assertThat(snap).hasSize(2);

        // Add more entries AFTER taking the snapshot
        store.addEntry("db.name=mydb");
        store.addEntry("db.user=admin");

        // Snapshot should still reflect the state at the time it was taken
        assertThat(snap).hasSize(2);
    }

    @Test
    void snapshotShouldNotReflectClear() {
        ConfigStore store = new ConfigStore("feature-flags");
        store.addEntry("feature.dark_mode=true");
        store.addEntry("feature.beta=false");

        List<String> snap = store.snapshot();
        assertThat(snap).hasSize(2);

        // Clearing the store should not affect the previously taken snapshot
        store.clear();

        assertThat(snap).hasSize(2);
        assertThat(snap).contains("feature.dark_mode=true");
    }

    @Test
    void multipleSnapshotsShouldBeIndependent() {
        ConfigStore store = new ConfigStore("settings");
        store.addEntry("key1=value1");

        List<String> snap1 = store.snapshot();

        store.addEntry("key2=value2");
        List<String> snap2 = store.snapshot();

        // snap1 should have 1 entry, snap2 should have 2
        assertThat(snap1).hasSize(1);
        assertThat(snap2).hasSize(2);
    }

    @Test
    void snapshotShouldContainCorrectEntries() {
        ConfigStore store = new ConfigStore("test");
        store.addEntry("alpha");
        store.addEntry("beta");

        List<String> snap = store.snapshot();
        assertThat(snap).containsExactly("alpha", "beta");
    }

    @Test
    void snapshotShouldBeUnmodifiable() {
        ConfigStore store = new ConfigStore("readonly-test");
        store.addEntry("entry1");

        List<String> snap = store.snapshot();

        // The snapshot itself should reject modifications
        org.junit.jupiter.api.Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> snap.add("hacked")
        );
    }
}
