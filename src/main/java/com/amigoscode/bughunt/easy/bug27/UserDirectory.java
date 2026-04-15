package com.amigoscode.bughunt.easy.bug27;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDirectory {

    public record User(long id, String name, String email) {}

    private final Map<Long, User> usersById = new HashMap<>();

    public void register(User user) {
        usersById.put(user.id(), user);
    }

    public Optional<User> findById(long id) {
        return Optional.ofNullable(usersById.get(id));
    }

    public String displayName(long id) {
        return findById(id)
                .map(User::name)
                .map(String::toUpperCase)
                .get();
    }

    public String emailDomain(long id) {
        return findById(id)
                .map(User::email)
                .map(e -> e.substring(e.indexOf('@') + 1))
                .orElse("unknown");
    }

    public int size() {
        return usersById.size();
    }
}
