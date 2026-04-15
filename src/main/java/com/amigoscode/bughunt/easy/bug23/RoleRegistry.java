package com.amigoscode.bughunt.easy.bug23;

import java.util.Arrays;
import java.util.List;

public class RoleRegistry {

    private final List<String> roles;
    private final String owner;

    public RoleRegistry(String owner, String... defaults) {
        this.owner = owner;
        this.roles = Arrays.asList(defaults);
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public int count() {
        return roles.size();
    }

    public String describe() {
        return owner + " has " + count() + " role(s)";
    }
}
