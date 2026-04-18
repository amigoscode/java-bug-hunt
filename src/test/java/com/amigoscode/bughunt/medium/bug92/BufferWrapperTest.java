package com.amigoscode.bughunt.medium.bug92;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BufferWrapperTest {

    @Test
    void newBufferShouldHaveRequestedLength() {
        BufferWrapper wrapper = new BufferWrapper("test", 10);

        // We created a buffer of size 10, so it should have 10 characters
        assertThat(wrapper.length()).isEqualTo(10);
    }

    @Test
    void charAtZeroShouldNotThrowOnSizedBuffer() {
        BufferWrapper wrapper = new BufferWrapper("test", 10);

        // A buffer created with size 10 should have a character at index 0
        char ch = wrapper.charAt(0);

        // The default char value is '\0' (null character)
        assertThat(ch).isNotNull();
    }

    @Test
    void requestedSizeShouldMatchConstructorArgument() {
        BufferWrapper wrapper = new BufferWrapper("sized", 50);

        assertThat(wrapper.requestedSize()).isEqualTo(50);
    }

    @Test
    void appendShouldAddToBuffer() {
        BufferWrapper wrapper = new BufferWrapper("append-test", 0);

        wrapper.append("hello");

        assertThat(wrapper.content()).isEqualTo("hello");
        assertThat(wrapper.length()).isEqualTo(5);
    }

    @Test
    void appendRepeatedShouldRepeatCharacter() {
        BufferWrapper wrapper = new BufferWrapper("repeat", 0);

        wrapper.appendRepeated('*', 5);

        assertThat(wrapper.content()).isEqualTo("*****");
    }
}
