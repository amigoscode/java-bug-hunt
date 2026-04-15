package com.amigoscode.bughunt.easy.bug19;

public class IpValidator {

    private static final int EXPECTED_OCTETS = 4;
    private static final int MIN_OCTET = 0;
    private static final int MAX_OCTET = 255;

    public int[] parse(String ip) {
        if (ip == null || ip.isBlank()) {
            throw new IllegalArgumentException("IP must not be blank");
        }
        String[] parts = ip.split(".");
        if (parts.length != EXPECTED_OCTETS) {
            throw new IllegalArgumentException("Expected 4 octets, got " + parts.length);
        }
        int[] octets = new int[EXPECTED_OCTETS];
        for (int i = 0; i < EXPECTED_OCTETS; i++) {
            octets[i] = Integer.parseInt(parts[i]);
        }
        return octets;
    }

    public boolean isValid(String ip) {
        try {
            int[] octets = parse(ip);
            for (int o : octets) {
                if (o < MIN_OCTET || o > MAX_OCTET) {
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String normalise(String ip) {
        int[] octets = parse(ip);
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }
}
