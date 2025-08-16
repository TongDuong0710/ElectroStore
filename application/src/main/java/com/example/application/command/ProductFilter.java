package com.example.application.command;

import java.math.BigDecimal;

public record ProductFilter(
        String category,
        BigDecimal priceMin,
        BigDecimal priceMax,
        Boolean available
) {}
