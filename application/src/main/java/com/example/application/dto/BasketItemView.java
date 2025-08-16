package com.example.application.dto;

import java.math.BigDecimal;

public record BasketItemView(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice
) {}
