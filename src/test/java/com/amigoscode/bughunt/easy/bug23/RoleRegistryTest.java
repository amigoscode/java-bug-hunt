package com.amigoscode.bughunt.easy.bug23;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleRegistryTest {

    @Test
    void startsWithDefaultRoles() {
        RoleRegistry r = new RoleRegistry("ada", "USER", "AUTHOR");
        assertThat(r.count()).isEqualTo(2);
        assertThat(r.hasRole("USER")).isTrue();
    }

    @Test
    void addRoleGrowsRegistry() {
        RoleRegistry r = new RoleRegistry("ada", "USER");
        r.addRole("ADMIN");

        assertThat(r.count()).isEqualTo(2);
        assertThat(r.hasRole("ADMIN")).isTrue();
    }

    @Test
    void removeRoleShrinksRegistry() {
        RoleRegistry r = new RoleRegistry("ada", "USER", "AUTHOR");
        r.removeRole("AUTHOR");

        assertThat(r.count()).isEqualTo(1);
    }
}
