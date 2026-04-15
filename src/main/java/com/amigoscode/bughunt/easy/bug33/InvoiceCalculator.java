package com.amigoscode.bughunt.easy.bug33;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InvoiceCalculator {

    public record LineItem(String description, int quantity, double unitPrice) {}

    private final List<LineItem> lines = new ArrayList<>();
    private final BigDecimal taxRate;

    public InvoiceCalculator(double taxRatePercent) {
        this.taxRate = new BigDecimal(taxRatePercent).divide(new BigDecimal(100));
    }

    public void addLine(String description, int quantity, double unitPrice) {
        lines.add(new LineItem(description, quantity, unitPrice));
    }

    public BigDecimal subtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (LineItem l : lines) {
            BigDecimal unit = new BigDecimal(l.unitPrice());
            total = total.add(unit.multiply(BigDecimal.valueOf(l.quantity())));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax() {
        return subtotal().multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal total() {
        return subtotal().add(tax()).setScale(2, RoundingMode.HALF_UP);
    }

    public int lineCount() {
        return lines.size();
    }
}
