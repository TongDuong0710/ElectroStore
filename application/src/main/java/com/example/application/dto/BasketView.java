package com.example.application.dto;

import java.util.List;

public record BasketView(
        String userId,
        List<BasketItemView> items
) {}

