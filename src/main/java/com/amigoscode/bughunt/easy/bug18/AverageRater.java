package com.amigoscode.bughunt.easy.bug18;

import java.util.ArrayList;
import java.util.List;

public class AverageRater {

    private final String productName;
    private final List<Integer> ratings = new ArrayList<>();
    private int totalScore = 0;

    public AverageRater(String productName) {
        this.productName = productName;
    }

    public void addRating(int score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Rating must be 1-5: " + score);
        }
        ratings.add(score);
        totalScore += score;
    }

    public int reviewCount() {
        return ratings.size();
    }

    public int averageRounded() {
        if (ratings.isEmpty()) {
            return 0;
        }
        return (int) Math.round(totalScore / ratings.size());
    }

    public String summary() {
        return productName + ": " + averageRounded() + "/5 (" + reviewCount() + " reviews)";
    }
}
