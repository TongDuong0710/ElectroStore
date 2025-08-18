package com.example.application.dto;

public record AddToBasketDto(
        String userId,
        Long productId,
        int quantity
) {}

