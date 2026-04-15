package com.amigoscode.bughunt.easy.bug42;

public class BinarySearch {

    private final int[] sorted;

    public BinarySearch(int[] sortedAscending) {
        validateSorted(sortedAscending);
        this.sorted = sortedAscending.clone();
    }

    public int indexOf(int target) {
        int low = 0;
        int high = sorted.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int value = sorted[mid];
            if (value == target) {
                return mid;
            }
            if (value < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public boolean contains(int target) {
        return indexOf(target) >= 0;
    }

    public int size() {
        return sorted.length;
    }

    private static void validateSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                throw new IllegalArgumentException("Array must be sorted ascending");
            }
        }
    }
}
