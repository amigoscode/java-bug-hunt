package com.amigoscode.bughunt.easy.bug35;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Playlist {

    public record Track(String title, String artist, int durationSeconds) {}

    private final String name;
    private final List<Track> tracks;

    public Playlist(String name, List<Track> tracks) {
        this.name = name;
        this.tracks = tracks;
    }

    public void sortByDuration() {
        Collections.sort(tracks, Comparator.comparingInt(Track::durationSeconds));
    }

    public void sortByTitle() {
        Collections.sort(tracks, Comparator.comparing(Track::title));
    }

    public List<Track> tracks() {
        return List.copyOf(tracks);
    }

    public int totalDuration() {
        return tracks.stream().mapToInt(Track::durationSeconds).sum();
    }

    public String name() {
        return name;
    }
}
