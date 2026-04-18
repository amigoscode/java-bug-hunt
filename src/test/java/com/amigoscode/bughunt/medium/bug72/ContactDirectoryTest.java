package com.amigoscode.bughunt.medium.bug72;

import com.amigoscode.bughunt.medium.bug72.ContactDirectory.Contact;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ContactDirectoryTest {

    @Test
    void emailToNameMapShouldHandleDuplicateEmails() {
        List<Contact> contacts = List.of(
                new Contact("Alice Smith", "shared@example.com", "555-0001"),
                new Contact("Bob Jones", "unique@example.com", "555-0002"),
                new Contact("Carol White", "shared@example.com", "555-0003")
        );

        ContactDirectory directory = new ContactDirectory(contacts);

        // When two contacts share the same email, the map should keep
        // the latest (last) entry rather than throwing an exception
        Map<String, String> map = directory.emailToNameMap();

        assertThat(map).containsKey("shared@example.com");
        assertThat(map).containsKey("unique@example.com");
        assertThat(map.get("shared@example.com")).isEqualTo("Carol White");
    }

    @Test
    void emailToNameMapShouldWorkWithUniqueEmails() {
        List<Contact> contacts = List.of(
                new Contact("Alice", "alice@example.com", null),
                new Contact("Bob", "bob@example.com", null)
        );

        ContactDirectory directory = new ContactDirectory(contacts);
        Map<String, String> map = directory.emailToNameMap();

        assertThat(map).hasSize(2);
        assertThat(map.get("alice@example.com")).isEqualTo("Alice");
        assertThat(map.get("bob@example.com")).isEqualTo("Bob");
    }

    @Test
    void emailToNameMapShouldHandleMultipleDuplicateGroups() {
        List<Contact> contacts = List.of(
                new Contact("A1", "a@test.com", null),
                new Contact("A2", "a@test.com", null),
                new Contact("B1", "b@test.com", null),
                new Contact("B2", "b@test.com", null),
                new Contact("C1", "c@test.com", null)
        );

        ContactDirectory directory = new ContactDirectory(contacts);
        Map<String, String> map = directory.emailToNameMap();

        assertThat(map).hasSize(3);
    }

    @Test
    void findByEmailShouldReturnMatchingContact() {
        List<Contact> contacts = List.of(
                new Contact("Alice", "alice@example.com", "555-0001")
        );

        ContactDirectory directory = new ContactDirectory(contacts);

        assertThat(directory.findByEmail("alice@example.com"))
                .isPresent()
                .get()
                .extracting(Contact::name)
                .isEqualTo("Alice");
    }

    @Test
    void searchByNameShouldBeCaseInsensitive() {
        List<Contact> contacts = List.of(
                new Contact("Alice Smith", "alice@example.com", null),
                new Contact("Bob SMITH", "bob@example.com", null),
                new Contact("Carol Jones", "carol@example.com", null)
        );

        ContactDirectory directory = new ContactDirectory(contacts);
        List<Contact> results = directory.searchByName("smith");

        assertThat(results).hasSize(2);
    }
}
