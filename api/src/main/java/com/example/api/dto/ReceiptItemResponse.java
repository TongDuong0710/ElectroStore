package com.example.api.dto;

public record ReceiptItemResponse(
        Long productId,
        String name,
        int quantity,
        double unitPrice,
        double totalPrice,
        String appliedDeal
) {}
