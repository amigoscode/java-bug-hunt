package com.amigoscode.bughunt.easy.bug48;

public class AgeRange {

    private final String label;
    private final int minInclusive;
    private final int maxInclusive;

    public AgeRange(String label, int minInclusive, int maxInclusive) {
        if (minInclusive > maxInclusive) {
            throw new IllegalArgumentException("min > max");
        }
        this.label = label;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    public boolean contains(int age) {
        return age > minInclusive && age < maxInclusive;
    }

    public int span() {
        return maxInclusive - minInclusive + 1;
    }

    public String label() {
        return label;
    }

    public int min() {
        return minInclusive;
    }

    public int max() {
        return maxInclusive;
    }

    public String describe() {
        return label + " (" + minInclusive + "-" + maxInclusive + ")";
    }
}
