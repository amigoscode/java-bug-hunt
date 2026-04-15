package com.amigoscode.bughunt.easy.bug43;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DirectoryTest {

    @Test
    void leafDirectoryHasDepthOne() {
        Directory d = new Directory("empty");
        assertThat(d.depth()).isEqualTo(1);
    }

    @Test
    void twoLevelDirectoryHasDepthTwo() {
        Directory root = new Directory("root");
        root.addSubdir("child");
        assertThat(root.depth()).isEqualTo(2);
    }

    @Test
    void threeLevelDirectoryHasDepthThree() {
        Directory root = new Directory("root");
        Directory a = root.addSubdir("a");
        a.addSubdir("b");
        assertThat(root.depth()).isEqualTo(3);
    }

    @Test
    void totalFilesCountsRecursively() {
        Directory root = new Directory("root");
        root.addFile("a.txt");
        Directory sub = root.addSubdir("sub");
        sub.addFile("b.txt");
        sub.addFile("c.txt");

        assertThat(root.totalFiles()).isEqualTo(3);
    }
}
