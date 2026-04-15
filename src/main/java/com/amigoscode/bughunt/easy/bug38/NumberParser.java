package com.amigoscode.bughunt.easy.bug38;

public class NumberParser {

    private final int defaultValue;

    public NumberParser(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int parse(String input) {
        if (input == null) {
            return defaultValue;
        }
        return Integer.parseInt(input);
    }

    public int parseOrDefault(String input) {
        try {
            return parse(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int sum(String a, String b) {
        return parseOrDefault(a) + parseOrDefault(b);
    }

    public int defaultValue() {
        return defaultValue;
    }
}
