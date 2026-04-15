package com.amigoscode.bughunt.easy.bug03;

public class RoleChecker {

    public boolean isAdmin(String role) {
        String normalized = role.trim().toUpperCase();
        return normalized == "ADMIN";
    }
}
