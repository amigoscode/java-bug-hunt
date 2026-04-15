package com.amigoscode.bughunt.easy.bug21;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Integer> stock = new HashMap<>();
    private final String warehouseName;

    public Inventory(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void set(String sku, int quantity) {
        stock.put(sku, quantity);
    }

    public Integer get(String sku) {
        return stock.get(sku);
    }

    public boolean sameStockLevel(String skuA, String skuB) {
        Integer a = stock.get(skuA);
        Integer b = stock.get(skuB);
        if (a == null || b == null) {
            return false;
        }
        return a == b;
    }

    public String summary() {
        return warehouseName + " holds " + stock.size() + " SKUs";
    }
}
