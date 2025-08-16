package com.example.api.dto;

import java.util.List;

public record ReceiptResponse(
        List<ReceiptItemResponse> items,
        double totalBeforeDiscount,
        double totalDiscount,
        double finalTotal
) {}
