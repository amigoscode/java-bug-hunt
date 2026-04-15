package com.amigoscode.bughunt.easy.bug03;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleCheckerTest {

    private final RoleChecker checker = new RoleChecker();

    @Test
    void recognisesAdminRole() {
        assertThat(checker.isAdmin(" admin ")).isTrue();
    }

    @Test
    void rejectsNonAdminRole() {
        assertThat(checker.isAdmin("user")).isFalse();
    }
}
