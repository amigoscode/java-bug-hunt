package com.amigoscode.bughunt.easy.bug26;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LeaderboardTest {

    @Test
    void championHasHighestScore() {
        Leaderboard lb = new Leaderboard("chess");
        lb.record("ada", 100);
        lb.record("bob", 50);
        lb.record("cat", 200);

        assertThat(lb.champion().player()).isEqualTo("cat");
    }

    @Test
    void topTwoInDescendingOrder() {
        Leaderboard lb = new Leaderboard("chess");
        lb.record("ada", 100);
        lb.record("bob", 50);
        lb.record("cat", 200);
        lb.record("dee", 150);

        assertThat(lb.topN(2))
                .extracting(Leaderboard.Entry::player)
                .containsExactly("cat", "dee");
    }
}
