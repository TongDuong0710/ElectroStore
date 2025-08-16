package com.example.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReceiptDto(
        List<ReceiptItemDto> items,
        BigDecimal totalOriginal,
        BigDecimal totalDiscount,
        BigDecimal totalFinal
) {}
