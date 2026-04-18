package com.amigoscode.bughunt.medium.bug59;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnvironmentConfigTest {

    @Test
    void devConfigShouldLoadSuccessfully() {
        EnvironmentConfig config = new EnvironmentConfig("dev");

        assertThat(config.environment()).isEqualTo("dev");
        assertThat(config.size()).isGreaterThan(0);
    }

    @Test
    void prodConfigShouldUseProductionDbHost() {
        EnvironmentConfig config = new EnvironmentConfig("prod");

        assertThat(config.getRequired("db.host"))
                .isEqualTo("prod.db.example.com");
    }

    @Test
    void jdbcUrlShouldContainEnvironment() {
        EnvironmentConfig config = new EnvironmentConfig("staging");

        assertThat(config.jdbcUrl())
                .contains("staging_db");
    }

    @Test
    void configShouldContainAllExpectedKeys() {
        EnvironmentConfig config = new EnvironmentConfig("dev");

        assertThat(config.keys()).contains(
                "db.host", "db.port", "db.name",
                "app.name", "app.env"
        );
    }
}
