package com.amigoscode.bughunt.easy.bug34;

import java.util.regex.Pattern;

public class UrlMatcher {

    private static final String PROTOCOL_REGEX = "https?://";
    private final Pattern pattern;

    public UrlMatcher() {
        this.pattern = Pattern.compile(PROTOCOL_REGEX);
    }

    public boolean isValidUrl(String candidate) {
        if (candidate == null || candidate.isBlank()) {
            return false;
        }
        return pattern.matcher(candidate).matches();
    }

    public boolean hasProtocol(String candidate) {
        if (candidate == null || candidate.isBlank()) {
            return false;
        }
        return pattern.matcher(candidate).find();
    }

    public String describe(String candidate) {
        if (isValidUrl(candidate)) {
            return candidate + " is a full URL";
        }
        if (hasProtocol(candidate)) {
            return candidate + " has a protocol but is not a full URL";
        }
        return candidate + " has no protocol";
    }
}
