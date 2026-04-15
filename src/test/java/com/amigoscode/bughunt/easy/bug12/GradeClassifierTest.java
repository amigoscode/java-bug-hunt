package com.amigoscode.bughunt.easy.bug12;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GradeClassifierTest {

    private final GradeClassifier classifier = new GradeClassifier();

    @Test
    void ninetyIsAnA() {
        assertThat(classifier.classify(90)).isEqualTo('A');
    }

    @Test
    void eightyIsAB() {
        assertThat(classifier.classify(80)).isEqualTo('B');
    }

    @Test
    void seventyIsAC() {
        assertThat(classifier.classify(70)).isEqualTo('C');
    }

    @Test
    void sixtyIsAD() {
        assertThat(classifier.classify(60)).isEqualTo('D');
    }

    @Test
    void fiftyNineIsAnF() {
        assertThat(classifier.classify(59)).isEqualTo('F');
    }
}
