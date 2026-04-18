package com.amigoscode.bughunt.medium.bug71;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Extracts and analyses tags from a collection of articles.
 * Each article has a title, author, and an optional list of tags.
 */
public class TagExtractor {

    /**
     * Represents a published article with optional tags.
     *
     * @param title  the article title
     * @param author the article author
     * @param tags   the list of tags, may be null if untagged
     */
    public record Article(String title, String author, List<String> tags) {

        public Article {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(author, "author must not be null");
            // tags is intentionally nullable -- not every article is tagged
        }
    }

    /**
     * Collects all distinct tags from every article, sorted alphabetically.
     *
     * BUG: If an article's tags() returns null, calling .stream() on it
     * throws a NullPointerException. The code assumes tags is never null.
     *
     * @param articles the list of articles to process
     * @return a sorted, distinct list of all tags
     */
    public List<String> allTags(List<Article> articles) {
        return articles.stream()
                .flatMap(a -> a.tags().stream())  // BUG: tags() might return null
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Counts how many articles are associated with each tag.
     *
     * @param articles the list of articles to process
     * @return a map of tag to article count
     */
    public Map<String, Long> tagCounts(List<Article> articles) {
        return articles.stream()
                .flatMap(a -> a.tags().stream())  // same bug propagated
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));
    }

    /**
     * Returns the total number of tag usages across all articles
     * (including duplicates).
     *
     * @param articles the list of articles
     * @return total tag usage count
     */
    public long totalTagUsages(List<Article> articles) {
        return articles.stream()
                .flatMap(a -> a.tags().stream())  // same bug propagated
                .count();
    }

    /**
     * Finds all articles that have at least one tag matching the given tag.
     *
     * @param articles the list of articles
     * @param tag      the tag to search for
     * @return list of matching articles
     */
    public List<Article> articlesWithTag(List<Article> articles, String tag) {
        Objects.requireNonNull(tag, "tag must not be null");
        return articles.stream()
                .filter(a -> a.tags() != null && a.tags().contains(tag))
                .toList();
    }

    /**
     * Returns the number of untagged articles (those with null or empty tags).
     *
     * @param articles the list of articles
     * @return count of untagged articles
     */
    public long untaggedCount(List<Article> articles) {
        return articles.stream()
                .filter(a -> a.tags() == null || a.tags().isEmpty())
                .count();
    }
}
