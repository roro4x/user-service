package com.example.user_service.model;

import java.util.UUID;

public record User(
        UUID id,
        String email
) {
}
