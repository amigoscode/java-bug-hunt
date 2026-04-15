package com.amigoscode.bughunt.easy.bug37;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserProfileTest {

    @Test
    void retainsTagsFromConstructor() {
        UserProfile p = new UserProfile("ada", List.of("java", "math"));

        assertThat(p.hasTag("java")).isTrue();
        assertThat(p.tags()).hasSize(2);
    }

    @Test
    void callerMutatingOriginalListDoesNotAffectProfile() {
        List<String> original = new ArrayList<>(List.of("java", "math"));
        UserProfile p = new UserProfile("ada", original);

        original.add("injected");

        assertThat(p.tags()).containsExactly("java", "math");
    }
}
