package com.example.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReceiptResponse(
        List<ReceiptItemResponse> items,
        BigDecimal totalBeforeDiscount,
        BigDecimal totalDiscount,
        BigDecimal totalFinal
) {}
