package com.amigoscode.bughunt.medium.bug58;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Renders shapes onto a canvas. Supports batch operations
 * like replacing, filtering, and summarizing shapes.
 */
public class ShapeRenderer {

    public static class Shape {
        private final String color;

        public Shape(String color) {
            this.color = color;
        }

        public String color() {
            return color;
        }

        public String describe() {
            return "Shape(" + color + ")";
        }
    }

    public static class Circle extends Shape {
        private final double radius;

        public Circle(String color, double radius) {
            super(color);
            this.radius = radius;
        }

        public double radius() {
            return radius;
        }

        @Override
        public String describe() {
            return "Circle(" + color() + ", r=" + radius + ")";
        }
    }

    public static class Square extends Shape {
        private final double side;

        public Square(String color, double side) {
            super(color);
            this.side = side;
        }

        public double side() {
            return side;
        }

        @Override
        public String describe() {
            return "Square(" + color() + ", s=" + side + ")";
        }
    }

    /**
     * Replaces the first shape in the array with the given replacement.
     * BUG: Java arrays are covariant — a Square[] can be passed as Shape[],
     * but storing a Circle into a Square[] throws ArrayStoreException.
     */
    public void replaceFirst(Shape[] shapes, Shape replacement) {
        if (shapes == null || shapes.length == 0) {
            throw new IllegalArgumentException("shapes array must not be empty");
        }
        shapes[0] = replacement;
    }

    /**
     * Returns descriptions of all shapes in the array.
     */
    public List<String> describeAll(Shape[] shapes) {
        return Arrays.stream(shapes)
                .map(Shape::describe)
                .collect(Collectors.toList());
    }

    /**
     * Fills the entire array with the given shape.
     */
    public void fillAll(Shape[] shapes, Shape filler) {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = filler;
        }
    }

    /**
     * Counts shapes matching a given color.
     */
    public long countByColor(Shape[] shapes, String color) {
        return Arrays.stream(shapes)
                .filter(s -> s.color().equals(color))
                .count();
    }

    /**
     * Returns a summary line: "N shapes: [desc1, desc2, ...]".
     */
    public String summary(Shape[] shapes) {
        String descriptions = Arrays.stream(shapes)
                .map(Shape::describe)
                .collect(Collectors.joining(", "));
        return shapes.length + " shapes: [" + descriptions + "]";
    }
}
