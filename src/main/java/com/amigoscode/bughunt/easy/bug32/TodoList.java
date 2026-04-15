package com.amigoscode.bughunt.easy.bug32;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TodoList {

    public static class Item {
        private final String title;
        private boolean done;

        public Item(String title) {
            this.title = title;
            this.done = false;
        }

        public String title() {
            return title;
        }

        public boolean isDone() {
            return done;
        }

        public void markDone() {
            this.done = true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Item other)) return false;
            return done == other.done && Objects.equals(title, other.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, done);
        }
    }

    private final Set<Item> items = new HashSet<>();
    private final String listName;

    public TodoList(String listName) {
        this.listName = listName;
    }

    public Item add(String title) {
        Item i = new Item(title);
        items.add(i);
        return i;
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public int size() {
        return items.size();
    }

    public String name() {
        return listName;
    }
}
