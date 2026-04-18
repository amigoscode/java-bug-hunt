package com.amigoscode.bughunt.medium.bug52;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCatalogTest {

    @Test
    void productRemainsAccessibleAfterMutatingName() {
        ProductCatalog catalog = new ProductCatalog();
        ProductCatalog.Product laptop = new ProductCatalog.Product("Laptop", "Electronics", 999.99);
        catalog.addProduct(laptop, 10);

        // Mutate the product's name after insertion
        laptop.setName("Gaming Laptop");

        // The catalog should still find this product
        assertThat(catalog.getStock(laptop)).isPresent().hasValue(10);
    }

    @Test
    void productRemainsAccessibleAfterMutatingCategory() {
        ProductCatalog catalog = new ProductCatalog();
        ProductCatalog.Product phone = new ProductCatalog.Product("Phone", "Mobile", 699.00);
        catalog.addProduct(phone, 25);

        phone.setCategory("Smartphones");

        assertThat(catalog.getStock(phone)).isPresent().hasValue(25);
    }

    @Test
    void totalProductsUnchangedAfterKeyMutation() {
        ProductCatalog catalog = new ProductCatalog();
        ProductCatalog.Product tablet = new ProductCatalog.Product("Tablet", "Electronics", 499.00);
        catalog.addProduct(tablet, 5);

        tablet.setName("iPad");

        assertThat(catalog.totalProducts()).isEqualTo(1);
        assertThat(catalog.removeProduct(tablet)).isTrue();
        assertThat(catalog.totalProducts()).isEqualTo(0);
    }
}
