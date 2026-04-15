package com.amigoscode.bughunt.easy.bug27;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDirectoryTest {

    @Test
    void displaysRegisteredUserNameInUppercase() {
        UserDirectory dir = new UserDirectory();
        dir.register(new UserDirectory.User(1L, "ada", "ada@example.com"));

        assertThat(dir.displayName(1L)).isEqualTo("ADA");
    }

    @Test
    void returnsUnknownForMissingUser() {
        UserDirectory dir = new UserDirectory();

        assertThat(dir.displayName(999L)).isEqualTo("UNKNOWN");
    }
}
