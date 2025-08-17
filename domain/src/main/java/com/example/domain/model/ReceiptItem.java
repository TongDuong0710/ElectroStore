package com.example.domain.model;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@Builder
@With
public record ReceiptItem(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal originalSubtotal, // unitPrice * quantity
        BigDecimal discount,         // total discount for this line
        BigDecimal finalSubtotal     // originalSubtotal - discount
) {}
