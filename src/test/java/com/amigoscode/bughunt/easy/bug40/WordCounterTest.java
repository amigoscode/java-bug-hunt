package com.amigoscode.bughunt.easy.bug40;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WordCounterTest {

    @Test
    void countsNewWord() {
        WordCounter wc = new WordCounter("test");
        wc.ingest("hello world");

        assertThat(wc.countFor("hello")).isEqualTo(1);
        assertThat(wc.countFor("world")).isEqualTo(1);
    }

    @Test
    void countsRepeatedWord() {
        WordCounter wc = new WordCounter("test");
        wc.ingest("hello hello hello");

        assertThat(wc.countFor("hello")).isEqualTo(3);
    }

    @Test
    void countsAcrossMultipleLines() {
        WordCounter wc = new WordCounter("test");
        wc.ingest("java is great");
        wc.ingest("java is fun");

        assertThat(wc.countFor("java")).isEqualTo(2);
        assertThat(wc.countFor("is")).isEqualTo(2);
    }
}
