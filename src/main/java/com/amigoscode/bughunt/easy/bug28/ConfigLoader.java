package com.amigoscode.bughunt.easy.bug28;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigLoader {

    private final Path baseDir;

    public ConfigLoader(Path baseDir) {
        this.baseDir = baseDir;
    }

    public Properties load(String filename) {
        Path target = baseDir.resolve(filename);
        Properties props = new Properties();
        try {
            try (var reader = Files.newBufferedReader(target)) {
                props.load(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return props;
    }

    public String loadValue(String filename, String key) {
        Properties p = load(filename);
        String value = p.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing key: " + key);
        }
        return value;
    }

    public Path baseDir() {
        return baseDir;
    }
}
