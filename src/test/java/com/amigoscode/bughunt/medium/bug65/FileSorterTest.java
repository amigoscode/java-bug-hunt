package com.amigoscode.bughunt.medium.bug65;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileSorterTest {

    @Test
    void filesWithSameExtensionAreSortedByName() {
        FileSorter sorter = new FileSorter();

        List<FileSorter.FileEntry> files = List.of(
                new FileSorter.FileEntry("zebra", "txt"),
                new FileSorter.FileEntry("apple", "txt"),
                new FileSorter.FileEntry("mango", "txt")
        );

        List<FileSorter.FileEntry> sorted = sorter.sortByExtensionThenName(files);

        // Within the same extension, files should be alphabetical by name
        assertThat(sorted).extracting(FileSorter.FileEntry::getName)
                .containsExactly("apple", "mango", "zebra");
    }

    @Test
    void filesSortedByExtensionFirstThenByName() {
        FileSorter sorter = new FileSorter();

        List<FileSorter.FileEntry> files = List.of(
                new FileSorter.FileEntry("notes", "txt"),
                new FileSorter.FileEntry("image", "png"),
                new FileSorter.FileEntry("alpha", "txt"),
                new FileSorter.FileEntry("data", "csv"),
                new FileSorter.FileEntry("beta", "png")
        );

        List<FileSorter.FileEntry> sorted = sorter.sortByExtensionThenName(files);

        assertThat(sorted).extracting(FileSorter.FileEntry::getFullName)
                .containsExactly("data.csv", "beta.png", "image.png", "alpha.txt", "notes.txt");
    }

    @Test
    void parseFileNameSplitsCorrectly() {
        FileSorter.FileEntry entry = FileSorter.parseFileName("report.pdf");

        assertThat(entry.getName()).isEqualTo("report");
        assertThat(entry.getExtension()).isEqualTo("pdf");
    }

    @Test
    void countByExtensionReturnsCorrectCount() {
        FileSorter sorter = new FileSorter();

        List<FileSorter.FileEntry> files = List.of(
                new FileSorter.FileEntry("a", "txt"),
                new FileSorter.FileEntry("b", "txt"),
                new FileSorter.FileEntry("c", "pdf")
        );

        assertThat(sorter.countByExtension(files, "txt")).isEqualTo(2);
        assertThat(sorter.countByExtension(files, "pdf")).isEqualTo(1);
    }

    @Test
    void getExtensionGroupsReturnsSortedDistinctExtensions() {
        FileSorter sorter = new FileSorter();

        List<FileSorter.FileEntry> files = List.of(
                new FileSorter.FileEntry("a", "pdf"),
                new FileSorter.FileEntry("b", "txt"),
                new FileSorter.FileEntry("c", "pdf"),
                new FileSorter.FileEntry("d", "csv")
        );

        assertThat(sorter.getExtensionGroups(files))
                .containsExactly("csv", "pdf", "txt");
    }
}
