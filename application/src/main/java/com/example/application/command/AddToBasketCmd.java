package com.example.application.command;

public record AddToBasketCmd(
        String userId,
        Long productId,
        int quantity
) {}

