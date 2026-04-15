package com.amigoscode.bughunt.easy.bug12;

public class GradeClassifier {

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 100;

    public char classify(int score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new IllegalArgumentException("Score out of range: " + score);
        }
        if (score > 90) {
            return 'A';
        } else if (score > 80) {
            return 'B';
        } else if (score > 70) {
            return 'C';
        } else if (score > 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public boolean isPassing(int score) {
        return classify(score) != 'F';
    }

    public String describe(int score) {
        char grade = classify(score);
        String label = switch (grade) {
            case 'A' -> "Excellent";
            case 'B' -> "Good";
            case 'C' -> "Satisfactory";
            case 'D' -> "Needs improvement";
            default -> "Fail";
        };
        return score + " -> " + grade + " (" + label + ")";
    }
}
