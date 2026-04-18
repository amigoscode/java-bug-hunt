package com.amigoscode.bughunt.medium.bug71;

import com.amigoscode.bughunt.medium.bug71.TagExtractor.Article;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TagExtractorTest {

    private final TagExtractor extractor = new TagExtractor();

    @Test
    void allTagsShouldHandleArticlesWithNullTags() {
        List<Article> articles = List.of(
                new Article("Java Streams", "Alice", List.of("java", "streams")),
                new Article("Untitled Draft", "Bob", null),  // null tags
                new Article("Spring Boot Guide", "Carol", List.of("java", "spring"))
        );

        List<String> tags = extractor.allTags(articles);

        // Should gracefully skip the null-tagged article
        assertThat(tags).containsExactly("java", "spring", "streams");
    }

    @Test
    void allTagsShouldReturnEmptyListWhenAllArticlesHaveNullTags() {
        List<Article> articles = List.of(
                new Article("Draft 1", "Alice", null),
                new Article("Draft 2", "Bob", null)
        );

        List<String> tags = extractor.allTags(articles);

        assertThat(tags).isEmpty();
    }

    @Test
    void tagCountsShouldHandleNullTags() {
        List<Article> articles = List.of(
                new Article("Post A", "Alice", List.of("java", "spring")),
                new Article("Post B", "Bob", null),
                new Article("Post C", "Carol", List.of("java"))
        );

        var counts = extractor.tagCounts(articles);

        assertThat(counts).containsEntry("java", 2L);
        assertThat(counts).containsEntry("spring", 1L);
    }

    @Test
    void totalTagUsagesShouldHandleNullTags() {
        List<Article> articles = List.of(
                new Article("Post A", "Alice", List.of("a", "b", "c")),
                new Article("Post B", "Bob", null),
                new Article("Post C", "Carol", List.of("d"))
        );

        long total = extractor.totalTagUsages(articles);

        assertThat(total).isEqualTo(4);
    }

    @Test
    void allTagsShouldReturnSortedDistinctTags() {
        List<Article> articles = List.of(
                new Article("P1", "A", List.of("zulu", "alpha")),
                new Article("P2", "B", List.of("alpha", "bravo"))
        );

        List<String> tags = extractor.allTags(articles);

        assertThat(tags).containsExactly("alpha", "bravo", "zulu");
    }
}
