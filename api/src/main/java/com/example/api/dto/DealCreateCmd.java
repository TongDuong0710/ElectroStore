package com.example.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealCreateCmd(
        Long productId,
        String dealType,
        BigDecimal discountPercentage,
        LocalDateTime expirationTime
) {}
