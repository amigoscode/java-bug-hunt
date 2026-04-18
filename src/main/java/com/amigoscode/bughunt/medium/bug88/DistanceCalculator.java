package com.amigoscode.bughunt.medium.bug88;

import java.util.List;
import java.util.Objects;

/**
 * Computes absolute distances between integer values, supporting single
 * pairs, arrays, and cumulative path distances.
 *
 * <p>BUG: {@link #distance(int, int)} uses {@code Math.abs(a - b)} which
 * fails when the subtraction overflows. For example,
 * {@code Integer.MAX_VALUE - (-1)} overflows to a negative number, and
 * {@code Math.abs(Integer.MIN_VALUE)} returns {@code Integer.MIN_VALUE}
 * (still negative). The result should always be non-negative, but it is not.
 */
public class DistanceCalculator {

    /**
     * Returns the absolute distance between two integers.
     *
     * <p>BUG: {@code Math.abs(a - b)} overflows for extreme values.
     *
     * @param a the first value
     * @param b the second value
     * @return the absolute distance (should be non-negative)
     */
    public long distance(int a, int b) {
        // BUG: a - b can overflow, and Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE
        return Math.abs(a - b);
    }

    /**
     * Returns the distance between two points in a 1-D array of positions.
     *
     * @param positions the array of positions
     * @param i         index of the first position
     * @param j         index of the second position
     * @return the absolute distance between positions[i] and positions[j]
     * @throws IndexOutOfBoundsException if either index is out of range
     */
    public long distanceBetween(int[] positions, int i, int j) {
        Objects.requireNonNull(positions, "positions must not be null");
        return distance(positions[i], positions[j]);
    }

    /**
     * Computes the total path distance when visiting the given waypoints
     * sequentially.
     *
     * @param waypoints ordered list of waypoints
     * @return the sum of consecutive distances
     */
    public long totalPathDistance(List<Integer> waypoints) {
        Objects.requireNonNull(waypoints, "waypoints must not be null");
        if (waypoints.size() < 2) {
            return 0;
        }
        long total = 0;
        for (int k = 1; k < waypoints.size(); k++) {
            total += distance(waypoints.get(k - 1), waypoints.get(k));
        }
        return total;
    }

    /**
     * Returns the index pair (i, j) with the maximum distance in the array.
     * If the array has fewer than two elements, returns {@code {-1, -1}}.
     *
     * @param values the array of values
     * @return an int array of length 2 containing the indices
     */
    public int[] farthestPair(int[] values) {
        Objects.requireNonNull(values, "values must not be null");
        if (values.length < 2) {
            return new int[]{-1, -1};
        }
        int bestI = 0, bestJ = 1;
        long bestDist = distance(values[0], values[1]);
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                long d = distance(values[i], values[j]);
                if (d > bestDist) {
                    bestDist = d;
                    bestI = i;
                    bestJ = j;
                }
            }
        }
        return new int[]{bestI, bestJ};
    }

    /**
     * Returns the minimum distance between any consecutive pair in the list.
     *
     * @param values the list of values (must have at least 2 elements)
     * @return the minimum consecutive distance
     * @throws IllegalArgumentException if fewer than 2 elements
     */
    public long minConsecutiveDistance(List<Integer> values) {
        Objects.requireNonNull(values, "values must not be null");
        if (values.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 values");
        }
        long min = Long.MAX_VALUE;
        for (int k = 1; k < values.size(); k++) {
            long d = distance(values.get(k - 1), values.get(k));
            if (d < min) {
                min = d;
            }
        }
        return min;
    }
}
