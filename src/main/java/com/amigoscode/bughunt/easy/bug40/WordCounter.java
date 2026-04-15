package com.amigoscode.bughunt.easy.bug40;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter {

    private final Map<String, Integer> counts = new HashMap<>();
    private final String corpusName;

    public WordCounter(String corpusName) {
        this.corpusName = corpusName;
    }

    public void ingest(String line) {
        if (line == null || line.isBlank()) {
            return;
        }
        String[] words = line.toLowerCase().split("\\s+");
        for (String w : words) {
            String clean = w.replaceAll("[^a-z]", "");
            if (clean.isEmpty()) continue;
            Integer current = counts.get(clean);
            counts.put(clean, current + 1);
        }
    }

    public int countFor(String word) {
        return counts.getOrDefault(word.toLowerCase(), 0);
    }

    public int uniqueWords() {
        return counts.size();
    }

    public List<String> topWords(int n) {
        return counts.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    public String corpusName() {
        return corpusName;
    }
}
