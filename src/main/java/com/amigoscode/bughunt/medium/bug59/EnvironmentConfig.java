package com.amigoscode.bughunt.medium.bug59;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Holds environment-specific configuration for a service.
 * Builds an immutable config map based on the target environment
 * (e.g. "dev", "staging", "prod").
 */
public class EnvironmentConfig {

    private final String environment;
    private final Map<String, String> config;

    public EnvironmentConfig(String environment) {
        this.environment = environment;
        // BUG: "db.host" appears twice — Map.of() throws
        // IllegalArgumentException on duplicate keys.
        this.config = Map.of(
                "db.host", "localhost",
                "db.port", "5432",
                "db.name", environment + "_db",
                "db.pool.size", "10",
                "db.timeout.ms", "5000",
                "db.host", environment + ".db.example.com",
                "app.name", "my-service",
                "app.env", environment
        );
    }

    /**
     * Retrieves a required config value. Throws if the key is missing.
     */
    public String getRequired(String key) {
        String value = config.get(key);
        if (value == null) {
            throw new IllegalStateException(
                    "Missing required config key: " + key
            );
        }
        return value;
    }

    /**
     * Retrieves an optional config value.
     */
    public Optional<String> get(String key) {
        return Optional.ofNullable(config.get(key));
    }

    /**
     * Returns all configuration keys.
     */
    public Set<String> keys() {
        return config.keySet();
    }

    /**
     * Returns the environment name.
     */
    public String environment() {
        return environment;
    }

    /**
     * Returns the number of config entries.
     */
    public int size() {
        return config.size();
    }

    /**
     * Checks whether a key exists in the config.
     */
    public boolean hasKey(String key) {
        return config.containsKey(key);
    }

    /**
     * Returns the database connection string built from config values.
     */
    public String jdbcUrl() {
        String host = getRequired("db.host");
        String port = getRequired("db.port");
        String name = getRequired("db.name");
        return "jdbc:postgresql://" + host + ":" + port + "/" + name;
    }

    @Override
    public String toString() {
        return "EnvironmentConfig[" + environment + "] " + config;
    }
}
