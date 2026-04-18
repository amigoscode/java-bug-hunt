package com.amigoscode.bughunt.medium.bug52;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductCatalog {

    public static class Product {
        private String name;
        private String category;
        private final double price;

        public Product(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Objects.equals(name, product.name)
                    && Objects.equals(category, product.category);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, category);
        }

        @Override
        public String toString() {
            return name + " [" + category + "] $" + String.format("%.2f", price);
        }
    }

    private final Map<Product, Integer> stock = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        stock.merge(product, quantity, Integer::sum);
    }

    public Optional<Integer> getStock(Product product) {
        return Optional.ofNullable(stock.get(product));
    }

    public boolean removeProduct(Product product) {
        return stock.remove(product) != null;
    }

    public int totalProducts() {
        return stock.size();
    }

    public int totalUnits() {
        return stock.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Set<Product> findByCategory(String category) {
        return stock.keySet().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toSet());
    }

    public Map<Product, Integer> snapshot() {
        return Map.copyOf(stock);
    }

    public String report() {
        StringBuilder sb = new StringBuilder("Catalog Report\n");
        sb.append("Total products: ").append(totalProducts()).append("\n");
        sb.append("Total units: ").append(totalUnits()).append("\n");
        stock.forEach((product, qty) ->
                sb.append("  ").append(product).append(" x").append(qty).append("\n"));
        return sb.toString();
    }
}
