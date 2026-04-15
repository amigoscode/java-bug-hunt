package com.amigoscode.bughunt.easy.bug24;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private final List<String> items = new ArrayList<>();
    private final String customer;

    public ShoppingCart(String customer) {
        this.customer = customer;
    }

    public void add(String item) {
        items.add(item);
    }

    public void removeAllContaining(String substring) {
        for (String item : items) {
            if (item.toLowerCase().contains(substring.toLowerCase())) {
                items.remove(item);
            }
        }
    }

    public int size() {
        return items.size();
    }

    public List<String> items() {
        return List.copyOf(items);
    }

    public String summary() {
        return customer + "'s cart: " + size() + " items";
    }
}
