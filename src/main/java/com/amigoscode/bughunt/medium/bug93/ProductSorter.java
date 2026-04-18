package com.amigoscode.bughunt.medium.bug93;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Sorts products by price (in cents) and provides utility methods
 * for finding the cheapest and most expensive products.
 *
 * Prices are stored as integers representing cents to avoid
 * floating-point precision issues.
 */
public class ProductSorter {

    /**
     * A product with a name and price in cents.
     */
    public record Product(String name, int priceCents) {
        public Product {
            Objects.requireNonNull(name, "name must not be null");
        }
    }

    private final List<Product> products = new ArrayList<>();

    /**
     * Adds a product to the sorter.
     *
     * @param product the product to add
     */
    public void add(Product product) {
        Objects.requireNonNull(product, "product must not be null");
        products.add(product);
    }

    /**
     * Returns all products sorted by price in ascending order.
     *
     * BUG: The comparator uses subtraction to compare prices:
     * {@code (a, b) -> a.priceCents() - b.priceCents()}. This overflows
     * when one price is near {@code Integer.MAX_VALUE} and another is near
     * {@code Integer.MIN_VALUE}, producing a wrong (negative) result instead
     * of a positive one, and corrupting the sort order.
     *
     * @return a new list of products sorted by price ascending
     */
    public List<Product> sortedByPrice() {
        List<Product> sorted = new ArrayList<>(products);
        // BUG: subtraction overflow when prices are far apart
        sorted.sort((a, b) -> a.priceCents() - b.priceCents());
        return sorted;
    }

    /**
     * Returns the cheapest product, or null if no products exist.
     * Uses the same buggy comparator internally.
     */
    public Product cheapest() {
        if (products.isEmpty()) {
            return null;
        }
        // BUG: same overflow problem
        return Collections.min(products,
                Comparator.comparingInt(p -> p.priceCents() - 0)
        );
    }

    /**
     * Returns the most expensive product, or null if no products exist.
     */
    public Product mostExpensive() {
        if (products.isEmpty()) {
            return null;
        }
        return Collections.max(products,
                (a, b) -> a.priceCents() - b.priceCents());
    }

    /**
     * Returns how many products are tracked.
     */
    public int count() {
        return products.size();
    }

    /**
     * Returns all products in insertion order.
     */
    public List<Product> all() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public String toString() {
        return "ProductSorter{count=" + products.size() + "}";
    }
}
