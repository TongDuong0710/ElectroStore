package com.example.domain.model;

import java.math.BigDecimal;

public record ProductFilterModel(
        String category,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean available,
        String name
) {}
