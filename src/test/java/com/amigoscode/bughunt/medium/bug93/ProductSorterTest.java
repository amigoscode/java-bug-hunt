package com.amigoscode.bughunt.medium.bug93;

import com.amigoscode.bughunt.medium.bug93.ProductSorter.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSorterTest {

    @Test
    void sortShouldHandleExtremelyFarApartPrices() {
        ProductSorter sorter = new ProductSorter();

        Product cheap = new Product("Penny candy", Integer.MIN_VALUE + 10);
        Product expensive = new Product("Luxury yacht", Integer.MAX_VALUE - 10);
        Product mid = new Product("Laptop", 100_000);

        sorter.add(expensive);
        sorter.add(cheap);
        sorter.add(mid);

        List<Product> sorted = sorter.sortedByPrice();

        // Cheapest should come first, most expensive last
        assertThat(sorted.get(0).name()).isEqualTo("Penny candy");
        assertThat(sorted.get(1).name()).isEqualTo("Laptop");
        assertThat(sorted.get(2).name()).isEqualTo("Luxury yacht");
    }

    @Test
    void mostExpensiveShouldReturnHighestPrice() {
        ProductSorter sorter = new ProductSorter();

        sorter.add(new Product("Budget phone", Integer.MIN_VALUE + 100));
        sorter.add(new Product("Gold bar", Integer.MAX_VALUE - 100));

        Product result = sorter.mostExpensive();

        assertThat(result.name()).isEqualTo("Gold bar");
    }

    @Test
    void sortShouldWorkWithNormalPrices() {
        ProductSorter sorter = new ProductSorter();

        sorter.add(new Product("Book", 1999));
        sorter.add(new Product("Pen", 299));
        sorter.add(new Product("Notebook", 599));

        List<Product> sorted = sorter.sortedByPrice();

        assertThat(sorted).extracting(Product::name)
                .containsExactly("Pen", "Notebook", "Book");
    }

    @Test
    void countShouldReflectAddedProducts() {
        ProductSorter sorter = new ProductSorter();

        sorter.add(new Product("A", 100));
        sorter.add(new Product("B", 200));

        assertThat(sorter.count()).isEqualTo(2);
    }

    @Test
    void cheapestShouldReturnLowestPrice() {
        ProductSorter sorter = new ProductSorter();

        sorter.add(new Product("Expensive", 50000));
        sorter.add(new Product("Cheap", 100));

        assertThat(sorter.cheapest().name()).isEqualTo("Cheap");
    }
}
