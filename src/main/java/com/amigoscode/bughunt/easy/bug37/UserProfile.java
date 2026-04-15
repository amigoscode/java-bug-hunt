package com.amigoscode.bughunt.easy.bug37;

import java.util.List;

public class UserProfile {

    private final String username;
    private final List<String> tags;
    private int reputation;

    public UserProfile(String username, List<String> tags) {
        this.username = username;
        this.tags = tags;
        this.reputation = 0;
    }

    public String username() {
        return username;
    }

    public List<String> tags() {
        return List.copyOf(tags);
    }

    public int reputation() {
        return reputation;
    }

    public void upvote() {
        reputation++;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public String describe() {
        return username + " (" + tags.size() + " tags, rep " + reputation + ")";
    }
}
