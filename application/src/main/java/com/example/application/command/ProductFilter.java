package com.example.application.command;

import java.math.BigDecimal;

public record ProductFilter(
        String category,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean available,
        String name
) {}
