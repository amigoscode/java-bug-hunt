package com.amigoscode.bughunt.easy.bug43;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    private final String name;
    private final List<Directory> subdirs = new ArrayList<>();
    private final List<String> fileNames = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public Directory addSubdir(String subName) {
        Directory d = new Directory(subName);
        subdirs.add(d);
        return d;
    }

    public void addFile(String fileName) {
        fileNames.add(fileName);
    }

    public int totalFiles() {
        int total = fileNames.size();
        for (Directory sub : subdirs) {
            total += sub.totalFiles();
        }
        return total;
    }

    public int depth() {
        if (subdirs.isEmpty()) {
            return 1;
        }
        int max = 0;
        for (Directory sub : subdirs) {
            max = Math.max(max, sub.depth());
        }
        return max;
    }

    public String name() {
        return name;
    }

    public List<Directory> subdirs() {
        return List.copyOf(subdirs);
    }
}
