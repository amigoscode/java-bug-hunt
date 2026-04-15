package com.amigoscode.bughunt.easy.bug44;

public class Fibonacci {

    private final int cacheSize;
    private final long[] cache;

    public Fibonacci(int cacheSize) {
        if (cacheSize < 0) {
            throw new IllegalArgumentException("Cache size must be non-negative");
        }
        this.cacheSize = cacheSize;
        this.cache = new long[cacheSize];
    }

    public long compute(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci is undefined for negative n: " + n);
        }
        if (n == 0) return 0;
        if (n == 1) return 1;

        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }

    public long cached(int n) {
        if (n < cacheSize && cache[n] != 0) {
            return cache[n];
        }
        long value = compute(n);
        if (n < cacheSize) {
            cache[n] = value;
        }
        return value;
    }

    public int cacheSize() {
        return cacheSize;
    }
}
