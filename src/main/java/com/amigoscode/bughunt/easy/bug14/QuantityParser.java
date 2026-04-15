package com.amigoscode.bughunt.easy.bug14;

import java.util.ArrayList;
import java.util.List;

public class QuantityParser {

    private int errorCount = 0;
    private final List<String> invalidInputs = new ArrayList<>();

    public int parseOrDefault(String raw, int fallback) {
        if (raw == null) {
            return fallback;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            // ignore — fall through to default
        }
        return fallback;
    }

    public int sumAll(List<String> values, int fallback) {
        int total = 0;
        for (String v : values) {
            total += parseOrDefault(v, fallback);
        }
        return total;
    }

    public int totalErrors() {
        return errorCount;
    }

    public List<String> invalidInputs() {
        return List.copyOf(invalidInputs);
    }
}
