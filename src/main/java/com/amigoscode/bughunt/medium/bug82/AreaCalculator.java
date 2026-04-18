package com.amigoscode.bughunt.medium.bug82;

import java.util.List;
import java.util.Objects;

/**
 * Calculates the area of various shapes using a sealed hierarchy and
 * {@code instanceof} pattern matching.
 *
 * BUG: The {@link #area(Shape)} method handles {@link Circle} and
 * {@link Rectangle} but forgets the {@link Triangle} case entirely.
 * Triangles silently fall through to the default return of 0.
 */
public class AreaCalculator {

    /**
     * Sealed interface representing a geometric shape.
     * Only {@link Circle}, {@link Rectangle}, and {@link Triangle} are permitted.
     */
    public sealed interface Shape permits Circle, Rectangle, Triangle {
        /** Returns a human-readable description of this shape. */
        String describe();
    }

    /**
     * A circle defined by its radius.
     */
    public record Circle(double radius) implements Shape {
        public Circle {
            if (radius < 0) throw new IllegalArgumentException("radius must be non-negative");
        }

        @Override
        public String describe() {
            return "Circle(radius=" + radius + ")";
        }
    }

    /**
     * A rectangle defined by its width and height.
     */
    public record Rectangle(double width, double height) implements Shape {
        public Rectangle {
            if (width < 0) throw new IllegalArgumentException("width must be non-negative");
            if (height < 0) throw new IllegalArgumentException("height must be non-negative");
        }

        @Override
        public String describe() {
            return "Rectangle(width=" + width + ", height=" + height + ")";
        }
    }

    /**
     * A triangle defined by its base and height.
     */
    public record Triangle(double base, double height) implements Shape {
        public Triangle {
            if (base < 0) throw new IllegalArgumentException("base must be non-negative");
            if (height < 0) throw new IllegalArgumentException("height must be non-negative");
        }

        @Override
        public String describe() {
            return "Triangle(base=" + base + ", height=" + height + ")";
        }
    }

    /**
     * Calculates the area of the given shape.
     *
     * <p>BUG: The Triangle case is missing -- triangles return 0.
     *
     * @param shape the shape whose area to calculate
     * @return the area of the shape
     * @throws NullPointerException if shape is null
     */
    public double area(Shape shape) {
        Objects.requireNonNull(shape, "shape must not be null");
        if (shape instanceof Circle c) return Math.PI * c.radius() * c.radius();
        if (shape instanceof Rectangle r) return r.width() * r.height();
        // BUG: Triangle is never handled -- falls through to 0
        return 0;
    }

    /**
     * Calculates the total area of a list of shapes.
     *
     * @param shapes the list of shapes
     * @return the sum of all areas
     */
    public double totalArea(List<Shape> shapes) {
        Objects.requireNonNull(shapes, "shapes must not be null");
        return shapes.stream()
                .mapToDouble(this::area)
                .sum();
    }

    /**
     * Returns a formatted summary line for the given shape.
     *
     * @param shape the shape to summarise
     * @return a string like "Circle(radius=5.0) -> area=78.54"
     */
    public String summary(Shape shape) {
        Objects.requireNonNull(shape, "shape must not be null");
        return shape.describe() + " -> area=" + String.format("%.2f", area(shape));
    }

    /**
     * Finds the shape with the largest area from the list.
     *
     * @param shapes the list of shapes
     * @return the shape with the maximum area, or null if list is empty
     */
    public Shape largest(List<Shape> shapes) {
        Objects.requireNonNull(shapes, "shapes must not be null");
        Shape best = null;
        double bestArea = -1;
        for (Shape s : shapes) {
            double a = area(s);
            if (a > bestArea) {
                bestArea = a;
                best = s;
            }
        }
        return best;
    }
}
