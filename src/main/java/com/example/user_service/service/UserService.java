package com.example.user_service.service;

import com.example.user_service.exception.MaxUsersExceededException;
import com.example.user_service.exception.UserAlreadyExistsException;
import com.example.user_service.exception.UserNotExistsException;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final int userLimit;

    public UserService(UserRepository userRepository,
                       @Value("${app.user-limit}") int maxUsers) {
        this.userRepository = userRepository;
        this.userLimit = maxUsers;
    }

    public User createUser(String email) {
        if (userRepository.findAll().size() >= userLimit) {
            throw new MaxUsersExceededException("Maximum number of users reached: " + userLimit);
        }
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
        User user = new User(UUID.randomUUID(), email);
        userRepository.save(user);
        return user;
    }

    public User findUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotExistsException("User with email " + email + " not exists");
        }
        return user;
    }

}
