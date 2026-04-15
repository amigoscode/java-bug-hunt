package com.amigoscode.bughunt.easy.bug39;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FeatureFlagsTest {

    @Test
    void explicitlyEnabledFlagIsTrue() {
        FeatureFlags f = new FeatureFlags("prod");
        f.enable("dark-mode");

        assertThat(f.isEnabled("dark-mode")).isTrue();
    }

    @Test
    void explicitlyDisabledFlagIsFalse() {
        FeatureFlags f = new FeatureFlags("prod");
        f.disable("dark-mode");

        assertThat(f.isEnabled("dark-mode")).isFalse();
    }

    @Test
    void unknownFlagIsFalseByDefault() {
        FeatureFlags f = new FeatureFlags("prod");

        assertThat(f.isEnabled("never-configured")).isFalse();
    }
}
