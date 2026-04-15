package com.amigoscode.bughunt.easy.bug16;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SafeDividerTest {

    private final SafeDivider divider = new SafeDivider();

    @Test
    void canDivideValidInputs() {
        assertThat(divider.canDivide(10, 2)).isTrue();
    }

    @Test
    void rejectsNullNumeratorWithoutNpe() {
        assertThat(divider.canDivide(null, 2)).isFalse();
    }

    @Test
    void rejectsNullDivisorWithoutNpe() {
        assertThat(divider.canDivide(10, null)).isFalse();
    }

    @Test
    void rejectsBothNullWithoutNpe() {
        assertThat(divider.canDivide(null, null)).isFalse();
    }

    @Test
    void rejectsZeroDivisor() {
        assertThat(divider.canDivide(10, 0)).isFalse();
    }
}
