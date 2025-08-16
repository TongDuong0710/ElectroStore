package com.example.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public record ReceiptItem(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal originalSubtotal, // unitPrice * quantity
        BigDecimal discount,         // total discount for this line
        BigDecimal finalSubtotal     // originalSubtotal - discount
) {}
