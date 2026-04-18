package com.amigoscode.bughunt.medium.bug65;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Sorts file entries by extension (primary) and then by name (secondary).
 * Used for organizing file listings in a consistent order.
 */
public class FileSorter {

    /**
     * Represents a file with a name and extension.
     */
    public static class FileEntry {
        private final String name;
        private final String extension;

        public FileEntry(String name, String extension) {
            this.name = Objects.requireNonNull(name);
            this.extension = Objects.requireNonNull(extension);
        }

        public String getName() {
            return name;
        }

        public String getExtension() {
            return extension;
        }

        public String getFullName() {
            return name + "." + extension;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FileEntry fileEntry = (FileEntry) o;
            return name.equals(fileEntry.name) && extension.equals(fileEntry.extension);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, extension);
        }

        @Override
        public String toString() {
            return getFullName();
        }
    }

    /**
     * Sorts a list of file entries by extension first, then by name
     * within the same extension group.
     *
     * @param files the list of files to sort
     * @return a new sorted list
     */
    public List<FileEntry> sortByExtensionThenName(List<FileEntry> files) {
        List<FileEntry> sorted = new ArrayList<>(files);

        Comparator<FileEntry> comparator = Comparator.comparing(FileEntry::getExtension);
        // BUG: thenComparing returns a NEW Comparator — the result is discarded.
        // The sort only uses the extension comparator, ignoring name ordering.
        comparator.thenComparing(FileEntry::getName);

        sorted.sort(comparator);
        return sorted;
    }

    /**
     * Groups files by extension and returns the group names in sorted order.
     */
    public List<String> getExtensionGroups(List<FileEntry> files) {
        return files.stream()
                .map(FileEntry::getExtension)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Counts how many files have the given extension.
     */
    public long countByExtension(List<FileEntry> files, String extension) {
        return files.stream()
                .filter(f -> f.getExtension().equals(extension))
                .count();
    }

    /**
     * Creates a FileEntry by parsing a filename like "report.pdf".
     */
    public static FileEntry parseFileName(String fullName) {
        int dotIndex = fullName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fullName.length() - 1) {
            throw new IllegalArgumentException("Invalid file name: " + fullName);
        }
        return new FileEntry(fullName.substring(0, dotIndex), fullName.substring(dotIndex + 1));
    }
}
