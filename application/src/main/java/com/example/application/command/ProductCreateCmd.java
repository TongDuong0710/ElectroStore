package com.example.application.command;

import java.math.BigDecimal;

public record ProductCreateCmd(
        String name,
        String category,
        BigDecimal price,
        int stock
) {}
