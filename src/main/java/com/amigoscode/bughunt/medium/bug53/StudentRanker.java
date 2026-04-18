package com.amigoscode.bughunt.medium.bug53;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class StudentRanker {

    public static class Student {
        private final String id;
        private final String name;
        private final int score;

        public Student(String id, String name, int score) {
            this.id = id;
            this.name = name;
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return Objects.equals(id, student.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return name + " (" + id + ") score=" + score;
        }
    }

    private final TreeSet<Student> rankings;

    public StudentRanker() {
        this.rankings = new TreeSet<>(Comparator.comparingInt(Student::getScore).reversed());
    }

    public void addStudent(Student student) {
        rankings.add(student);
    }

    public int size() {
        return rankings.size();
    }

    public Student topStudent() {
        if (rankings.isEmpty()) {
            throw new IllegalStateException("No students ranked yet");
        }
        return rankings.first();
    }

    public Student bottomStudent() {
        if (rankings.isEmpty()) {
            throw new IllegalStateException("No students ranked yet");
        }
        return rankings.last();
    }

    public List<Student> topN(int n) {
        return rankings.stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    public boolean containsStudent(Student student) {
        return rankings.contains(student);
    }

    public List<String> leaderboard() {
        int rank = 1;
        List<String> board = new java.util.ArrayList<>();
        for (Student s : rankings) {
            board.add(rank + ". " + s.getName() + " — " + s.getScore());
            rank++;
        }
        return board;
    }

    public double averageScore() {
        return rankings.stream()
                .mapToInt(Student::getScore)
                .average()
                .orElse(0.0);
    }
}
