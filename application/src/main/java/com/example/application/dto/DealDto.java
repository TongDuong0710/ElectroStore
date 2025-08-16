package com.example.application.dto;

import java.time.LocalDateTime;

public record DealDto(
        Long id,
        Long productId,
        String dealType,
        String description,
        LocalDateTime expirationDateTime
) {}
