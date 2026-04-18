package com.amigoscode.bughunt.medium.bug68;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NumberSequenceTest {

    @Test
    void secondIterationShouldProduceSameValues() {
        NumberSequence seq = new NumberSequence(5);

        List<Integer> firstPass = new ArrayList<>();
        for (int value : seq) {
            firstPass.add(value);
        }

        List<Integer> secondPass = new ArrayList<>();
        for (int value : seq) {
            secondPass.add(value);
        }

        // Both iterations should produce the same values
        assertThat(secondPass).isEqualTo(firstPass);
    }

    @Test
    void toListCalledTwiceShouldReturnSameValues() {
        NumberSequence seq = new NumberSequence(4, "test-seq");

        List<Integer> first = seq.toList();
        List<Integer> second = seq.toList();

        assertThat(second).containsExactlyElementsOf(first);
    }

    @Test
    void sumAfterToListShouldStillWork() {
        NumberSequence seq = new NumberSequence(5);

        // First consume via toList
        List<Integer> values = seq.toList();
        assertThat(values).containsExactly(0, 1, 2, 3, 4);

        // Then compute sum -- should still iterate correctly
        int total = seq.sum();
        assertThat(total).isEqualTo(10); // 0+1+2+3+4
    }

    @Test
    void firstIterationShouldProduceAllValues() {
        NumberSequence seq = new NumberSequence(3);

        List<Integer> values = new ArrayList<>();
        for (int value : seq) {
            values.add(value);
        }

        assertThat(values).containsExactly(0, 1, 2);
    }

    @Test
    void sequenceLabelShouldBeSet() {
        NumberSequence seq = new NumberSequence(10, "my-sequence");
        assertThat(seq.label()).isEqualTo("my-sequence");
    }
}
