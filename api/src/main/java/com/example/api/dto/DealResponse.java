package com.example.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DealResponse {
    private Long id;
    private Long productId;
    private String dealType;
    private LocalDateTime expirationDateTime;
}
