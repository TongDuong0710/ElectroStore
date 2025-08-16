package com.example.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DealCreateRequest {
    private Long productId;
    private String dealType; // e.g., "BUY_ONE_GET_ONE_50"
    private BigDecimal discountPercentage;
    private LocalDateTime expirationTime;
}

