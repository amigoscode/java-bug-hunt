package com.amigoscode.bughunt.medium.bug82;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class AreaCalculatorTest {

    private final AreaCalculator calc = new AreaCalculator();

    @Test
    void circleAreaShouldBePiRSquared() {
        var circle = new AreaCalculator.Circle(5);

        double area = calc.area(circle);

        assertThat(area).isCloseTo(Math.PI * 25, within(0.001));
    }

    @Test
    void rectangleAreaShouldBeWidthTimesHeight() {
        var rect = new AreaCalculator.Rectangle(4, 7);

        double area = calc.area(rect);

        assertThat(area).isCloseTo(28.0, within(0.001));
    }

    @Test
    void triangleAreaShouldBeHalfBaseTimesHeight() {
        var triangle = new AreaCalculator.Triangle(6, 10);

        double area = calc.area(triangle);

        // BUG: returns 0 because Triangle case is missing
        assertThat(area).isCloseTo(30.0, within(0.001));
    }

    @Test
    void totalAreaShouldSumAllShapes() {
        List<AreaCalculator.Shape> shapes = List.of(
                new AreaCalculator.Rectangle(2, 3),
                new AreaCalculator.Triangle(4, 5)
        );

        double total = calc.totalArea(shapes);

        // rectangle = 6, triangle = 10, total should be 16
        assertThat(total).isCloseTo(16.0, within(0.001));
    }

    @Test
    void largestShouldReturnShapeWithMaxArea() {
        var small = new AreaCalculator.Rectangle(1, 1);
        var big = new AreaCalculator.Triangle(100, 200);

        AreaCalculator.Shape largest = calc.largest(List.of(small, big));

        // Triangle area = 10000, Rectangle area = 1. Triangle should win.
        assertThat(largest).isEqualTo(big);
    }
}
