package com.example.domain.model;

import java.math.BigDecimal;

public record ProductFilterModel(
        String category,
        BigDecimal priceMin,
        BigDecimal priceMax,
        Boolean available
) {}
