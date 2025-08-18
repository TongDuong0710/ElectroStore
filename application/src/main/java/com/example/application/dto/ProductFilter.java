package com.example.application.dto;

import java.math.BigDecimal;

public record ProductFilter(
        String category,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean available,
        String name
) {}
