package com.amigoscode.bughunt.medium.bug80;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PermissionsTest {

    @Test
    void grantShouldAddNewPermission() {
        Permissions perms = new Permissions(Stream.of(
                new Permissions.Entry("read", "true"),
                new Permissions.Entry("write", "false")
        ));

        // Should be able to grant a new permission after initial load
        perms.grant("delete", "true");

        assertThat(perms.has("delete")).isTrue();
        assertThat(perms.get("delete")).contains("true");
    }

    @Test
    void grantShouldUpdateExistingPermission() {
        Permissions perms = new Permissions(Stream.of(
                new Permissions.Entry("write", "false")
        ));

        // Should be able to update an existing permission
        perms.grant("write", "true");

        assertThat(perms.get("write")).contains("true");
    }

    @Test
    void revokeShouldRemovePermission() {
        Permissions perms = new Permissions(Stream.of(
                new Permissions.Entry("admin", "true"),
                new Permissions.Entry("read", "true")
        ));

        boolean removed = perms.revoke("admin");

        assertThat(removed).isTrue();
        assertThat(perms.has("admin")).isFalse();
    }

    @Test
    void getShouldReturnInitialPermissions() {
        Permissions perms = new Permissions(Stream.of(
                new Permissions.Entry("read", "true")
        ));

        assertThat(perms.get("read")).contains("true");
        assertThat(perms.get("missing")).isEmpty();
    }

    @Test
    void sizeShouldReflectGrantsAndRevokes() {
        Permissions perms = new Permissions(Stream.of(
                new Permissions.Entry("a", "1"),
                new Permissions.Entry("b", "2")
        ));

        perms.grant("c", "3");

        assertThat(perms.size()).isEqualTo(3);
    }
}
