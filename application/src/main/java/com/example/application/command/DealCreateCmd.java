package com.example.application.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealCreateCmd(
        Long productId,
        String dealType,
        BigDecimal discountPercentage,
        String description,
        LocalDateTime expirationDateTime
) {}

