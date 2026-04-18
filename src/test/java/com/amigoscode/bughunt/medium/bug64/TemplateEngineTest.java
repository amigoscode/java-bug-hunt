package com.amigoscode.bughunt.medium.bug64;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateEngineTest {

    @Test
    void renderReplacesPlaceholderWithValue() {
        TemplateEngine engine = new TemplateEngine();

        String result = engine.render("Hello ${name}!", Map.of("name", "Ada"));

        assertThat(result).isEqualTo("Hello Ada!");
    }

    @Test
    void renderReplacesMultiplePlaceholders() {
        TemplateEngine engine = new TemplateEngine();

        String result = engine.render(
                "Dear ${title} ${lastName}, your order #${orderId} is ready.",
                Map.of("title", "Dr", "lastName", "Hopper", "orderId", "42")
        );

        assertThat(result).isEqualTo("Dear Dr Hopper, your order #42 is ready.");
    }

    @Test
    void renderUsesGlobalVariablesAsFallback() {
        TemplateEngine engine = new TemplateEngine();
        engine.setGlobalVariable("company", "Amigoscode");

        String result = engine.render(
                "Welcome to ${company}, ${user}!",
                Map.of("user", "Nelson")
        );

        assertThat(result).isEqualTo("Welcome to Amigoscode, Nelson!");
    }

    @Test
    void localVariablesOverrideGlobals() {
        TemplateEngine engine = new TemplateEngine();
        engine.setGlobalVariable("greeting", "Hello");

        String result = engine.render(
                "${greeting} world!",
                Map.of("greeting", "Hola")
        );

        assertThat(result).isEqualTo("Hola world!");
    }

    @Test
    void findUnresolvedPlaceholdersDetectsMissingKeys() {
        TemplateEngine engine = new TemplateEngine();

        var unresolved = engine.findUnresolvedPlaceholders(
                "Hello ${name}, your ${role} at ${company}",
                Map.of("name", "Ada")
        );

        assertThat(unresolved).containsExactlyInAnyOrder("role", "company");
    }
}
