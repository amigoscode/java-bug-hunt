package com.amigoscode.bughunt.easy.bug50;

import java.util.Objects;

public class Fingerprint {

    private final byte[] bytes;
    private final String algorithm;

    public Fingerprint(String algorithm, byte[] bytes) {
        if (algorithm == null || algorithm.isBlank()) {
            throw new IllegalArgumentException("Algorithm required");
        }
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("Bytes required");
        }
        this.algorithm = algorithm;
        this.bytes = bytes.clone();
    }

    public String algorithm() {
        return algorithm;
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    public int length() {
        return bytes.length;
    }

    public boolean matches(Fingerprint other) {
        if (other == null) return false;
        if (!algorithm.equals(other.algorithm)) return false;
        return bytes == other.bytes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm, java.util.Arrays.hashCode(bytes));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(algorithm).append(":");
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
