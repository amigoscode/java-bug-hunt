package com.amigoscode.bughunt.medium.bug73;

import com.amigoscode.bughunt.medium.bug73.StudentSorter.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentSorterTest {

    private final StudentSorter sorter = new StudentSorter();

    @Test
    void sortByGradeShouldPlaceNullGradesAtEnd() {
        List<Student> students = List.of(
                new Student("Alice", "B"),
                new Student("Bob", null),    // ungraded
                new Student("Carol", "A"),
                new Student("Dave", null),   // ungraded
                new Student("Eve", "C")
        );

        List<Student> sorted = sorter.sortByGrade(students);

        // Graded students should come first in alphabetical grade order
        assertThat(sorted.get(0).name()).isEqualTo("Carol");  // A
        assertThat(sorted.get(1).name()).isEqualTo("Alice");  // B
        assertThat(sorted.get(2).name()).isEqualTo("Eve");    // C

        // Ungraded students should be at the end
        assertThat(sorted.get(3).grade()).isNull();
        assertThat(sorted.get(4).grade()).isNull();
    }

    @Test
    void sortByGradeShouldHandleAllNullGrades() {
        List<Student> students = List.of(
                new Student("Alice", null),
                new Student("Bob", null)
        );

        List<Student> sorted = sorter.sortByGrade(students);

        assertThat(sorted).hasSize(2);
    }

    @Test
    void sortByGradeShouldHandleSingleNullGrade() {
        List<Student> students = List.of(
                new Student("Alice", "A"),
                new Student("Bob", null)
        );

        List<Student> sorted = sorter.sortByGrade(students);

        assertThat(sorted.get(0).name()).isEqualTo("Alice");
        assertThat(sorted.get(1).name()).isEqualTo("Bob");
    }

    @Test
    void bestGradeShouldIgnoreNullGrades() {
        List<Student> students = List.of(
                new Student("Alice", null),
                new Student("Bob", "C"),
                new Student("Carol", "A")
        );

        assertThat(sorter.bestGrade(students)).contains("A");
    }

    @Test
    void ungradedCountShouldCountNullGrades() {
        List<Student> students = List.of(
                new Student("Alice", "A"),
                new Student("Bob", null),
                new Student("Carol", null),
                new Student("Dave", "B")
        );

        assertThat(sorter.ungradedCount(students)).isEqualTo(2);
    }
}
