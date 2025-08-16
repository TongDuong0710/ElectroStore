package com.example.application.command;

public record RemoveFromBasketCmd(
        String userId,
        Long productId
) {}

