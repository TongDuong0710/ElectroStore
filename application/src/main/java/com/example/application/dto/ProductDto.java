package com.example.application.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String category,
        BigDecimal price,
        int stock,
        boolean available
) {}

