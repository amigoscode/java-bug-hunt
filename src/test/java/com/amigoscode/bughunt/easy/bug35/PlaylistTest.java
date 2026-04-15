package com.amigoscode.bughunt.easy.bug35;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlaylistTest {

    @Test
    void sortsByDurationAscending() {
        Playlist p = new Playlist("mix", List.of(
                new Playlist.Track("b", "x", 300),
                new Playlist.Track("a", "y", 100),
                new Playlist.Track("c", "z", 200)
        ));

        p.sortByDuration();

        assertThat(p.tracks())
                .extracting(Playlist.Track::title)
                .containsExactly("a", "c", "b");
    }
}
