package com.amigoscode.bughunt.medium.bug96;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataPipelineTest {

    @Test
    void sortedNamesShouldReturnAlphabeticalOrder() {
        DataPipeline pipeline = new DataPipeline(List.of(
                new DataPipeline.Person("Charlie", 30),
                new DataPipeline.Person("Alice", 25),
                new DataPipeline.Person("Bob", 28)
        ));

        List<String> sorted = pipeline.sortedNames();

        assertThat(sorted).containsExactly("Alice", "Bob", "Charlie");
    }

    @Test
    void sortedNamesOlderThanShouldFilterAndSort() {
        DataPipeline pipeline = new DataPipeline(List.of(
                new DataPipeline.Person("Zara", 40),
                new DataPipeline.Person("Eve", 20),
                new DataPipeline.Person("Dan", 35),
                new DataPipeline.Person("Amy", 18)
        ));

        List<String> sorted = pipeline.sortedNamesOlderThan(25);

        assertThat(sorted).containsExactly("Dan", "Zara");
    }

    @Test
    void sortedUpperCaseNamesShouldReturnUppercasedAndSorted() {
        DataPipeline pipeline = new DataPipeline(List.of(
                new DataPipeline.Person("charlie", 30),
                new DataPipeline.Person("alice", 25),
                new DataPipeline.Person("bob", 28)
        ));

        List<String> sorted = pipeline.sortedUpperCaseNames();

        assertThat(sorted).containsExactly("ALICE", "BOB", "CHARLIE");
    }

    @Test
    void countShouldReturnNumberOfPeople() {
        DataPipeline pipeline = new DataPipeline(List.of(
                new DataPipeline.Person("Alice", 25),
                new DataPipeline.Person("Bob", 28)
        ));

        assertThat(pipeline.count()).isEqualTo(2);
    }

    @Test
    void summaryShouldContainCount() {
        DataPipeline pipeline = new DataPipeline(List.of(
                new DataPipeline.Person("Alice", 25)
        ));

        assertThat(pipeline.summary()).contains("count=1");
    }
}
