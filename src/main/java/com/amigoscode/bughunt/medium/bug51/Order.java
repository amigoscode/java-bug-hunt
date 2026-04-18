package com.amigoscode.bughunt.medium.bug51;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public record LineItem(String product, int quantity, double unitPrice) {
        public double total() {
            return quantity * unitPrice;
        }
    }

    private final String orderId;
    private final String customer;
    private final List<LineItem> items;

    public Order(String orderId, String customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    private Order(String orderId, String customer, List<LineItem> items) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
    }

    public void addItem(String product, int quantity, double unitPrice) {
        items.add(new LineItem(product, quantity, unitPrice));
    }

    public Order copy(String newOrderId) {
        return new Order(newOrderId, customer, items);
    }

    public double grandTotal() {
        return items.stream().mapToDouble(LineItem::total).sum();
    }

    public int lineCount() {
        return items.size();
    }

    public List<LineItem> items() {
        return List.copyOf(items);
    }

    public String orderId() {
        return orderId;
    }

    public String customer() {
        return customer;
    }

    public String summary() {
        return orderId + " for " + customer + ": " + lineCount() + " items, $"
                + String.format("%.2f", grandTotal());
    }
}
