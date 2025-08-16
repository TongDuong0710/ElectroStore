package com.example.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(
        List<ReceiptItem> items,
        BigDecimal totalOriginal,
        BigDecimal totalDiscount,
        BigDecimal totalFinal
) {}
