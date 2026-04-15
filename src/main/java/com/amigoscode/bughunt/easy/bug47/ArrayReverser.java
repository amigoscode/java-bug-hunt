package com.amigoscode.bughunt.easy.bug47;

public class ArrayReverser {

    private int swapCount = 0;

    public int[] reversed(int[] input) {
        int[] copy = input.clone();
        reverseInPlace(copy);
        return copy;
    }

    public void reverseInPlace(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int j = arr.length - 1 - i;
            swap(arr, i, j);
        }
    }

    private void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
        swapCount++;
    }

    public int swapCount() {
        return swapCount;
    }

    public void resetSwapCount() {
        swapCount = 0;
    }

    public boolean isPalindrome(int[] arr) {
        int[] copy = reversed(arr);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != copy[i]) return false;
        }
        return true;
    }
}
