package com.amigoscode.bughunt.medium.bug84;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    @Test
    void findOrCreateShouldReturnExistingUser() {
        UserService service = new UserService();
        service.register(new UserService.User(1, "Alice"));

        UserService.User result = service.findOrCreate(1);

        assertThat(result.name()).isEqualTo("Alice");
    }

    @Test
    void findOrCreateShouldNotCreateDefaultWhenUserExists() {
        UserService service = new UserService();
        service.register(new UserService.User(1, "Alice"));

        service.findOrCreate(1);

        // BUG: defaultsCreated is 1 because orElse eagerly evaluates createDefault()
        assertThat(service.defaultsCreated()).isEqualTo(0);
    }

    @Test
    void findOrCreateShouldCreateDefaultWhenUserMissing() {
        UserService service = new UserService();

        UserService.User result = service.findOrCreate(999);

        assertThat(result.name()).isEqualTo("default");
        assertThat(service.defaultsCreated()).isEqualTo(1);
    }

    @Test
    void multipleLookupsOfExistingUserShouldNotCreateDefaults() {
        UserService service = new UserService();
        service.register(new UserService.User(1, "Alice"));
        service.register(new UserService.User(2, "Bob"));

        service.findOrCreate(1);
        service.findOrCreate(2);
        service.findOrCreate(1);

        // BUG: defaultsCreated is 3 -- one for each call, even though all users exist
        assertThat(service.defaultsCreated()).isEqualTo(0);
    }

    @Test
    void findUserShouldReturnEmptyForUnknownId() {
        UserService service = new UserService();

        assertThat(service.findUser(42)).isEmpty();
    }
}
