package com.example.api.dto;

import java.math.BigDecimal;

public record ReceiptItemResponse(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal originalSubtotal,
        BigDecimal discount,
        BigDecimal finalSubtotal,
        boolean appliedDeal
) {}
