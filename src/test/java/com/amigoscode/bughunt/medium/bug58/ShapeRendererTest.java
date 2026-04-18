package com.amigoscode.bughunt.medium.bug58;

import com.amigoscode.bughunt.medium.bug58.ShapeRenderer.Circle;
import com.amigoscode.bughunt.medium.bug58.ShapeRenderer.Shape;
import com.amigoscode.bughunt.medium.bug58.ShapeRenderer.Square;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShapeRendererTest {

    @Test
    void replaceFirstInSubtypeArrayShouldWork() {
        // Square[] is passed as Shape[] — Java allows this (covariance).
        // Replacing with a Circle should be fine from a Shape perspective.
        Shape[] shapes = new Square[]{
                new Square("red", 4.0),
                new Square("blue", 3.0)
        };

        ShapeRenderer renderer = new ShapeRenderer();
        renderer.replaceFirst(shapes, new Circle("green", 5.0));

        assertThat(shapes[0].describe()).isEqualTo("Circle(green, r=5.0)");
    }

    @Test
    void fillAllInSubtypeArrayShouldWork() {
        Shape[] shapes = new Square[]{
                new Square("red", 4.0),
                new Square("blue", 3.0),
                new Square("yellow", 2.0)
        };

        ShapeRenderer renderer = new ShapeRenderer();
        renderer.fillAll(shapes, new Circle("white", 1.0));

        assertThat(shapes).allSatisfy(s ->
                assertThat(s.describe()).isEqualTo("Circle(white, r=1.0)")
        );
    }

    @Test
    void describeAllWorksNormally() {
        Shape[] shapes = new Shape[]{
                new Circle("red", 2.0),
                new Square("blue", 3.0)
        };

        ShapeRenderer renderer = new ShapeRenderer();

        assertThat(renderer.describeAll(shapes))
                .containsExactly("Circle(red, r=2.0)", "Square(blue, s=3.0)");
    }
}
