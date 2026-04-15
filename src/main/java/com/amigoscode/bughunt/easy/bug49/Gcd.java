package com.amigoscode.bughunt.easy.bug49;

public class Gcd {

    public int gcd(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Inputs must be non-negative");
        }
        if (b == 0) {
            return a;
        }
        return gcd(a, a % b);
    }

    public int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int g = gcd(a, b);
        return (a / g) * b;
    }

    public boolean areCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    public int gcdOfAll(int... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Need at least one value");
        }
        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            result = gcd(result, values[i]);
        }
        return result;
    }
}
