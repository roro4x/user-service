package com.example.user_service.controller;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequest request) {
        User user = userService.createUser(request.email());
        return ResponseEntity.created(URI.create("/api/users/" + user.id())).build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUser(email));
    }
}
