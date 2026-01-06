package com.example.user_service.repository;

import com.example.user_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository {


    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public void save(User user) {
        users.put(user.id(), user);
    }

    public User findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.email().equals(email))
                .findFirst()
                .orElse(null);
    }

    public User findById(UUID id) {
        return users.get(id);
    }

    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.email().equals(email));
    }

    public Map<UUID, User> findAll() {
        return new ConcurrentHashMap<>(users);
    }
}
