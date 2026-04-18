package com.amigoscode.bughunt.medium.bug53;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentRankerTest {

    @Test
    void allStudentsKeptWhenTwoShareSameScore() {
        StudentRanker ranker = new StudentRanker();
        ranker.addStudent(new StudentRanker.Student("S1", "Alice", 95));
        ranker.addStudent(new StudentRanker.Student("S2", "Bob", 88));
        ranker.addStudent(new StudentRanker.Student("S3", "Charlie", 88));

        // All 3 students should be in the set, even though Bob and Charlie share score 88
        assertThat(ranker.size()).isEqualTo(3);
    }

    @Test
    void leaderboardContainsAllStudents() {
        StudentRanker ranker = new StudentRanker();
        ranker.addStudent(new StudentRanker.Student("S1", "Alice", 90));
        ranker.addStudent(new StudentRanker.Student("S2", "Bob", 90));
        ranker.addStudent(new StudentRanker.Student("S3", "Charlie", 85));

        assertThat(ranker.leaderboard()).hasSize(3);
    }

    @Test
    void containsFindsStudentWithDuplicateScore() {
        StudentRanker ranker = new StudentRanker();
        StudentRanker.Student alice = new StudentRanker.Student("S1", "Alice", 92);
        StudentRanker.Student bob = new StudentRanker.Student("S2", "Bob", 92);
        ranker.addStudent(alice);
        ranker.addStudent(bob);

        assertThat(ranker.containsStudent(alice)).isTrue();
        assertThat(ranker.containsStudent(bob)).isTrue();
    }
}
