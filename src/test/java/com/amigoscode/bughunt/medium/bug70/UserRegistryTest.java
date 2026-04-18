package com.amigoscode.bughunt.medium.bug70;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRegistryTest {

    @Test
    void registerNewUserShouldReturnTheirName() {
        UserRegistry registry = new UserRegistry("main");

        // Registering a brand new user should return the name we just registered
        String result = registry.registerOrGet(1L, "Alice");

        assertThat(result).isEqualTo("Alice");
    }

    @Test
    void registerExistingUserShouldReturnOriginalName() {
        UserRegistry registry = new UserRegistry("main");

        registry.registerOrGet(1L, "Alice");
        String result = registry.registerOrGet(1L, "Alice2");

        // Should return the original name, not the attempted override
        assertThat(result).isEqualTo("Alice");
    }

    @Test
    void registerMultipleNewUsersShouldReturnEachName() {
        UserRegistry registry = new UserRegistry("test");

        String alice = registry.registerOrGet(1L, "Alice");
        String bob = registry.registerOrGet(2L, "Bob");
        String carol = registry.registerOrGet(3L, "Carol");

        assertThat(alice).isEqualTo("Alice");
        assertThat(bob).isEqualTo("Bob");
        assertThat(carol).isEqualTo("Carol");
    }

    @Test
    void registerShouldNeverReturnNull() {
        UserRegistry registry = new UserRegistry("non-null");

        String result = registry.registerOrGet(42L, "TestUser");

        // registerOrGet should always return a non-null name
        assertThat(result).isNotNull();
    }

    @Test
    void registeredUserShouldBeFindable() {
        UserRegistry registry = new UserRegistry("lookup");

        registry.registerOrGet(10L, "Diana");

        assertThat(registry.isRegistered(10L)).isTrue();
        assertThat(registry.findById(10L)).contains("Diana");
    }
}
