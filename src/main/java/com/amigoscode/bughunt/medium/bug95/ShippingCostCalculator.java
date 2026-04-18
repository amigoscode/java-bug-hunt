package com.amigoscode.bughunt.medium.bug95;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Calculates shipping costs based on the destination region.
 * Supports various regions with flat-rate shipping prices.
 *
 * Used by the checkout service to determine delivery charges.
 */
public class ShippingCostCalculator {

    private final String warehouse;
    private final Map<String, Double> regionCosts;
    private int totalCalculations;

    /**
     * Creates a calculator for shipments from the given warehouse.
     * Initializes the region cost table.
     *
     * BUG: The cost map is populated with {@code Map.of()}, which returns
     * an unmodifiable map. However, the real bug is in the {@code cost}
     * method: it uses {@code regionCosts.get(region)} without checking for
     * null, then unboxes the result to a primitive {@code double}. For any
     * region not in the map, {@code get} returns {@code null}, and unboxing
     * {@code null} to {@code double} throws {@code NullPointerException}.
     *
     * @param warehouse the originating warehouse code
     */
    public ShippingCostCalculator(String warehouse) {
        this.warehouse = Objects.requireNonNull(warehouse, "warehouse must not be null");
        this.totalCalculations = 0;
        this.regionCosts = new HashMap<>();
        regionCosts.put("US", 5.99);
        regionCosts.put("EU", 8.99);
        regionCosts.put("UK", 7.99);
        regionCosts.put("CA", 6.49);
    }

    /**
     * Returns the shipping cost for the given region.
     *
     * BUG: When the region is not in the map, {@code regionCosts.get(region)}
     * returns {@code null}. The return type is {@code double} (primitive),
     * so Java tries to auto-unbox the {@code null} Double, causing a
     * {@code NullPointerException}.
     *
     * @param region the destination region code
     * @return the shipping cost in dollars
     * @throws NullPointerException if region is not in the cost table
     */
    public double cost(String region) {
        Objects.requireNonNull(region, "region must not be null");
        totalCalculations++;

        // BUG: get returns null for unknown regions, unboxing null -> NPE
        return regionCosts.get(region);
    }

    /**
     * Returns the shipping cost formatted as a price string.
     *
     * @param region the destination region code
     * @return a formatted price string like "$5.99"
     */
    public String formattedCost(String region) {
        return String.format("$%.2f", cost(region));
    }

    /**
     * Checks if a region is eligible for free shipping (cost under $6).
     *
     * @param region the destination region code
     * @return true if shipping is under $6.00
     */
    public boolean isFreeShippingEligible(String region) {
        return cost(region) < 6.00;
    }

    /**
     * Returns the warehouse code.
     */
    public String warehouse() {
        return warehouse;
    }

    /**
     * Returns how many times cost has been calculated.
     */
    public int totalCalculations() {
        return totalCalculations;
    }

    @Override
    public String toString() {
        return "ShippingCostCalculator{warehouse='" + warehouse
                + "', calculations=" + totalCalculations + "}";
    }
}
