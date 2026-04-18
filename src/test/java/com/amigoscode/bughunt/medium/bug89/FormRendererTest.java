package com.amigoscode.bughunt.medium.bug89;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormRendererTest {

    @Test
    void renderShouldPreserveInsertionOrder() {
        FormRenderer renderer = new FormRenderer("Registration");
        renderer.addField("First Name", "Jane");
        renderer.addField("Last Name", "Doe");
        renderer.addField("Email", "jane@example.com");
        renderer.addField("Password", "********");
        renderer.addField("Phone", "+1-555-0100");

        String html = renderer.render();

        // BUG: HashMap does not guarantee insertion order, so the labels
        // may appear in any order in the rendered HTML.
        int firstName = html.indexOf("First Name");
        int lastName = html.indexOf("Last Name");
        int email = html.indexOf("Email");
        int password = html.indexOf("Password");
        int phone = html.indexOf("Phone");

        assertThat(firstName).isLessThan(lastName);
        assertThat(lastName).isLessThan(email);
        assertThat(email).isLessThan(password);
        assertThat(password).isLessThan(phone);
    }

    @Test
    void labelSummaryShouldBeInInsertionOrder() {
        FormRenderer renderer = new FormRenderer("Contact");
        renderer.addField("Name", "Your name");
        renderer.addField("Email", "you@example.com");
        renderer.addField("Subject", "Topic");
        renderer.addField("Message", "Your message");

        // BUG: HashMap iteration order is not insertion order
        assertThat(renderer.labelSummary()).isEqualTo("Name, Email, Subject, Message");
    }

    @Test
    void fieldCountShouldMatchAdded() {
        FormRenderer renderer = new FormRenderer("Test");
        renderer.addField("A", "a");
        renderer.addField("B", "b");

        assertThat(renderer.fieldCount()).isEqualTo(2);
    }

    @Test
    void hasFieldShouldReturnTrueForExisting() {
        FormRenderer renderer = new FormRenderer("Test");
        renderer.addField("Email", "email");

        assertThat(renderer.hasField("Email")).isTrue();
        assertThat(renderer.hasField("Phone")).isFalse();
    }

    @Test
    void removeFieldShouldRemoveExisting() {
        FormRenderer renderer = new FormRenderer("Test");
        renderer.addField("Name", "name");

        assertThat(renderer.removeField("Name")).isTrue();
        assertThat(renderer.fieldCount()).isZero();
    }
}
