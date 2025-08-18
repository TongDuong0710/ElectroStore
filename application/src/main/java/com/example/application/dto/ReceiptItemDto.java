package com.example.application.dto;

import java.math.BigDecimal;

public record ReceiptItemDto(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal originalSubtotal,
        BigDecimal discount,
        BigDecimal finalSubtotal,
        boolean appliedDeal
) {}
