package com.example.user_service.controller;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.model.User;
import com.example.user_service.repository.InMemoryUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final InMemoryUserRepository userRepository;

    public UserController(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody
            @Valid
            UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.status(409).build();
        }
        User user = new User(UUID.randomUUID(), request.email());
        userRepository.save(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + user.id()))
                .build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
