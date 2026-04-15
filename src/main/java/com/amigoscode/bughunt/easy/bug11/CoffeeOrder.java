package com.amigoscode.bughunt.easy.bug11;

public class CoffeeOrder {

    private final String size;
    private final int quantity;
    private final boolean withExtraShot;

    public CoffeeOrder(String size, int quantity, boolean withExtraShot) {
        this.size = size;
        this.quantity = quantity;
        this.withExtraShot = withExtraShot;
    }

    public double unitPrice() {
        double price;
        switch (size) {
            case "S":
                price = 2.50;
                break;
            case "M":
                price = 3.00;
                break;
            case "L":
                price = 3.50;
            case "XL":
                price = 4.50;
                break;
            default:
                throw new IllegalArgumentException("Unknown size: " + size);
        }
        if (withExtraShot) {
            price += 0.75;
        }
        return price;
    }

    public double total() {
        return unitPrice() * quantity;
    }

    public String receipt() {
        return quantity + " x " + size + " coffee" +
                (withExtraShot ? " (extra shot)" : "") +
                " = $" + String.format("%.2f", total());
    }
}
