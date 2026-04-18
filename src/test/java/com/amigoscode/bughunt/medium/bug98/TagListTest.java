package com.amigoscode.bughunt.medium.bug98;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TagListTest {

    @Test
    void shouldStoreTagsIncludingNullsGracefully() {
        // A null tag should be filtered out or stored safely
        // BUG: List.of throws NullPointerException for null elements
        TagList tagList = new TagList("java", null, "spring");

        assertThat(tagList.size()).isEqualTo(2);
    }

    @Test
    void shouldHandleMultipleNullTags() {
        // Multiple nulls should be filtered out
        // BUG: List.of throws NullPointerException
        TagList tagList = new TagList("docker", null, null, "kubernetes");

        assertThat(tagList.size()).isEqualTo(2);
    }

    @Test
    void containsShouldFindExistingTag() {
        TagList tagList = new TagList("java", "spring", "boot");

        assertThat(tagList.contains("spring")).isTrue();
    }

    @Test
    void formattedShouldJoinTagsWithComma() {
        TagList tagList = new TagList("a", "b", "c");

        assertThat(tagList.formatted()).isEqualTo("a, b, c");
    }

    @Test
    void startingWithShouldFilterByPrefix() {
        TagList tagList = new TagList("spring-boot", "spring-cloud", "docker", "spring-data");

        assertThat(tagList.startingWith("spring")).hasSize(3);
    }
}
