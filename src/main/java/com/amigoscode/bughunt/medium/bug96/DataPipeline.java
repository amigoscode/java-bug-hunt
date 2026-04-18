package com.amigoscode.bughunt.medium.bug96;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A data pipeline that processes collections of {@code Person} records.
 * Provides methods for filtering, transforming, and sorting person data.
 *
 * Used by the reporting module to generate sorted name lists and summaries.
 */
public class DataPipeline {

    private final List<Person> people;

    /**
     * A simple record representing a person with a name and age.
     *
     * @param name the person's full name
     * @param age  the person's age in years
     */
    public record Person(String name, int age) {
        public Person {
            Objects.requireNonNull(name, "name must not be null");
            if (age < 0) {
                throw new IllegalArgumentException("age must not be negative");
            }
        }
    }

    /**
     * Creates a pipeline with the given list of people.
     *
     * @param people the people to process
     */
    public DataPipeline(List<Person> people) {
        this.people = Objects.requireNonNull(people, "people must not be null");
    }

    /**
     * Returns the names of all people, sorted alphabetically.
     *
     * BUG: {@code Stream.toList()} (Java 16+) returns an unmodifiable list.
     * Calling {@code Collections.sort()} on it throws
     * {@code UnsupportedOperationException}.
     *
     * @return a sorted list of names
     * @throws UnsupportedOperationException because the list from toList() is unmodifiable
     */
    public List<String> sortedNames() {
        List<String> names = people.stream()
                .map(Person::name)
                .toList();  // unmodifiable!
        Collections.sort(names);  // BUG: UnsupportedOperationException
        return names;
    }

    /**
     * Returns the names of people older than the given age, sorted alphabetically.
     *
     * BUG: Same issue -- {@code toList()} produces an unmodifiable list,
     * so in-place sorting fails.
     *
     * @param minAge the minimum age (exclusive)
     * @return a sorted list of names of people older than minAge
     * @throws UnsupportedOperationException because the list from toList() is unmodifiable
     */
    public List<String> sortedNamesOlderThan(int minAge) {
        List<String> names = people.stream()
                .filter(p -> p.age() > minAge)
                .map(Person::name)
                .toList();  // unmodifiable!
        Collections.sort(names);  // BUG: UnsupportedOperationException
        return names;
    }

    /**
     * Returns the names of all people in uppercase, sorted alphabetically.
     *
     * BUG: Same unmodifiable list issue with {@code toList()}.
     *
     * @return a sorted list of uppercased names
     * @throws UnsupportedOperationException because the list from toList() is unmodifiable
     */
    public List<String> sortedUpperCaseNames() {
        List<String> names = people.stream()
                .map(p -> p.name().toUpperCase())
                .toList();  // unmodifiable!
        Collections.sort(names);  // BUG: UnsupportedOperationException
        return names;
    }

    /**
     * Returns the number of people in the pipeline.
     *
     * @return the count of people
     */
    public int count() {
        return people.size();
    }

    /**
     * Returns a summary string showing the number of people and their names.
     *
     * @return a human-readable summary
     */
    public String summary() {
        return "DataPipeline{count=" + count()
                + ", people=" + people.stream().map(Person::name).toList() + "}";
    }

    @Override
    public String toString() {
        return summary();
    }
}
