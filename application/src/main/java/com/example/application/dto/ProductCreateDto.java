package com.example.application.dto;

import java.math.BigDecimal;

public record ProductCreateDto(
        String name,
        String category,
        BigDecimal price,
        int stock
) {}
