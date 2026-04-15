package com.amigoscode.bughunt.easy.bug15;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final List<String> lines = new ArrayList<>();
    private double grandTotal = 0.0;

    public String header(String shopName) {
        return "=== " + shopName + " ===";
    }

    public String addLine(String item, int quantity, double unitPrice) {
        double lineTotal = quantity * unitPrice;
        grandTotal += lineTotal;
        String formatted = String.format(
                "%-12s x%d @ $%d = $%.2f",
                item, quantity, unitPrice, lineTotal);
        lines.add(formatted);
        return formatted;
    }

    public double grandTotal() {
        return grandTotal;
    }

    public String footer() {
        return String.format("TOTAL: $%.2f", grandTotal);
    }

    public List<String> allLines() {
        return List.copyOf(lines);
    }
}
