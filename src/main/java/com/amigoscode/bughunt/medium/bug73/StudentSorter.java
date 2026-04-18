package com.amigoscode.bughunt.medium.bug73;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Sorts and queries students by their grade.
 * Grades are nullable -- a null grade means the student has not been graded yet.
 */
public class StudentSorter {

    /**
     * Represents a student with an optional grade.
     *
     * @param name  the student's name
     * @param grade the student's grade (A-F), may be null if not yet graded
     */
    public record Student(String name, String grade) {

        public Student {
            Objects.requireNonNull(name, "name must not be null");
            // grade is intentionally nullable
        }
    }

    /**
     * Sorts students by grade alphabetically.
     * Students with null grades should appear at the end.
     *
     * BUG: Comparator.comparing(Student::grade) throws NullPointerException
     * when grade is null, because the natural-order comparator does not
     * handle nulls.
     *
     * @param students the list of students to sort
     * @return a new sorted list
     */
    public List<Student> sortByGrade(List<Student> students) {
        return students.stream()
                .sorted(Comparator.comparing(Student::grade))  // BUG: NPE on null grade
                .toList();
    }

    /**
     * Returns the best (lowest alphabetically) grade in the list,
     * ignoring ungraded students.
     *
     * @param students the list of students
     * @return the best grade, or empty if no students are graded
     */
    public Optional<String> bestGrade(List<Student> students) {
        return students.stream()
                .map(Student::grade)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder());
    }

    /**
     * Groups students by grade and returns the group counts as a formatted string.
     * Ungraded students are grouped under "UNGRADED".
     *
     * @param students the list of students
     * @return formatted grade summary
     */
    public String gradeSummary(List<Student> students) {
        var grouped = students.stream()
                .collect(Collectors.groupingBy(
                        s -> s.grade() != null ? s.grade() : "UNGRADED",
                        Collectors.counting()
                ));
        return grouped.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey()))
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns all students who have been graded.
     *
     * @param students the list of students
     * @return students with non-null grades
     */
    public List<Student> gradedStudents(List<Student> students) {
        return students.stream()
                .filter(s -> s.grade() != null)
                .toList();
    }

    /**
     * Returns the number of students who have not yet been graded.
     *
     * @param students the list of students
     * @return count of ungraded students
     */
    public long ungradedCount(List<Student> students) {
        return students.stream()
                .filter(s -> s.grade() == null)
                .count();
    }
}
