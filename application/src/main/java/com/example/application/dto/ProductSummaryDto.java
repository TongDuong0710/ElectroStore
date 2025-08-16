package com.example.application.dto;

import java.math.BigDecimal;

public record ProductSummaryDto(
        Long id,
        String name,
        String category,
        BigDecimal price,
        boolean available
) {}
