package com.amigoscode.bughunt.easy.bug41;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class IntStack {

    private final List<Integer> data = new ArrayList<>();
    private final String label;

    public IntStack(String label) {
        this.label = label;
    }

    public void push(int value) {
        data.add(value);
    }

    public int pop() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("Stack is empty: " + label);
        }
        return data.remove(0);
    }

    public int peek() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("Stack is empty: " + label);
        }
        return data.get(0);
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public String label() {
        return label;
    }

    public String describe() {
        return label + " [" + size() + " items]";
    }
}
