package com.amigoscode.bughunt.easy.bug45;

public class Power {

    public long power(long base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative: " + exponent);
        }
        if (exponent == 0) {
            return 1L;
        }
        long half = power(base, exponent / 2);
        return half * half;
    }

    public long powerIterative(long base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative: " + exponent);
        }
        long result = 1L;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public boolean isPowerOfTwo(long n) {
        if (n <= 0) return false;
        return (n & (n - 1)) == 0;
    }
}
