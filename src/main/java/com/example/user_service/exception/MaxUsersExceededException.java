package com.example.user_service.exception;

public class MaxUsersExceededException extends RuntimeException {
    public MaxUsersExceededException(String message) {
        super(message);
    }
}
