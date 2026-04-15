package com.amigoscode.bughunt.easy.bug26;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {

    public record Entry(String player, int score) {}

    private final List<Entry> entries = new ArrayList<>();
    private final String gameTitle;

    public Leaderboard(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void record(String player, int score) {
        entries.add(new Entry(player, score));
    }

    public List<Entry> topN(int n) {
        List<Entry> sorted = new ArrayList<>(entries);
        sorted.sort((a, b) -> a.score() - b.score());
        return sorted.stream().limit(n).toList();
    }

    public Entry champion() {
        if (entries.isEmpty()) {
            throw new IllegalStateException("No entries yet");
        }
        return topN(1).get(0);
    }

    public int size() {
        return entries.size();
    }

    public String title() {
        return gameTitle + " leaderboard (" + size() + " players)";
    }
}
