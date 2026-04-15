package com.amigoscode.bughunt.easy.bug28;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigLoaderTest {

    @Test
    void missingFilePropagatesUsefulError(@TempDir Path tmp) throws IOException {
        ConfigLoader loader = new ConfigLoader(tmp);

        assertThatThrownBy(() -> loader.load("missing.properties"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing.properties")
                .hasCauseInstanceOf(IOException.class);
    }
}
