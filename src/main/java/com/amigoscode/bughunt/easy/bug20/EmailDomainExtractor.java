package com.amigoscode.bughunt.easy.bug20;

public class EmailDomainExtractor {

    public String domain(String email) {
        validateNotBlank(email);
        int at = email.indexOf('@');
        return email.substring(at + 1).toLowerCase();
    }

    public String localPart(String email) {
        validateNotBlank(email);
        int at = email.indexOf('@');
        return email.substring(0, at);
    }

    public boolean sameDomain(String emailA, String emailB) {
        return domain(emailA).equals(domain(emailB));
    }

    private void validateNotBlank(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
    }
}
