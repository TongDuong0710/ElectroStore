package com.example.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealCreateDto(
        Long productId,
        String dealType,
        BigDecimal discountPercentage,
        String description,
        LocalDateTime expirationDateTime
) {}

