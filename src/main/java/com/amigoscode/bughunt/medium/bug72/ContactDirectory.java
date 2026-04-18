package com.amigoscode.bughunt.medium.bug72;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A directory that indexes contacts by their email address.
 * Supports lookup, listing, and building a quick-access map.
 */
public class ContactDirectory {

    /**
     * Represents a contact entry.
     *
     * @param name  the contact's display name
     * @param email the contact's email address
     * @param phone the contact's phone number, may be null
     */
    public record Contact(String name, String email, String phone) {

        public Contact {
            Objects.requireNonNull(name, "name must not be null");
            Objects.requireNonNull(email, "email must not be null");
        }
    }

    private final List<Contact> contacts;

    /**
     * Creates a directory from a list of contacts.
     *
     * @param contacts the contacts to index
     */
    public ContactDirectory(List<Contact> contacts) {
        this.contacts = List.copyOf(contacts);
    }

    /**
     * Builds a map from email address to contact name for fast lookup.
     *
     * BUG: If two contacts share the same email, Collectors.toMap throws
     * an IllegalStateException because it does not allow duplicate keys
     * by default.
     *
     * @return a map of email to name
     */
    public Map<String, String> emailToNameMap() {
        return contacts.stream()
                .collect(Collectors.toMap(Contact::email, Contact::name));
        // BUG: no merge function -- duplicate emails cause IllegalStateException
    }

    /**
     * Looks up the first contact with the given email.
     *
     * @param email the email to search for
     * @return an Optional containing the contact, or empty if not found
     */
    public Optional<Contact> findByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return contacts.stream()
                .filter(c -> c.email().equals(email))
                .findFirst();
    }

    /**
     * Returns all contacts whose name contains the given substring
     * (case-insensitive).
     *
     * @param substring the text to search for in names
     * @return matching contacts
     */
    public List<Contact> searchByName(String substring) {
        Objects.requireNonNull(substring, "substring must not be null");
        String lower = substring.toLowerCase();
        return contacts.stream()
                .filter(c -> c.name().toLowerCase().contains(lower))
                .toList();
    }

    /**
     * Returns the total number of contacts in the directory.
     */
    public int size() {
        return contacts.size();
    }

    /**
     * Returns all distinct email domains (the part after '@').
     *
     * @return sorted list of domains
     */
    public List<String> allDomains() {
        return contacts.stream()
                .map(c -> c.email().substring(c.email().indexOf('@') + 1))
                .distinct()
                .sorted()
                .toList();
    }
}
