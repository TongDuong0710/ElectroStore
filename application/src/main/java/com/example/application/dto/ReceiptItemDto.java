package com.example.application.dto;

import java.math.BigDecimal;

public record ReceiptItemDto(
        Long productId,
        String name,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal originalSubtotal,
        BigDecimal discount,
        BigDecimal finalSubtotal
) {}
