package com.example.api.dto;

import java.util.List;

public record BasketResponse(List<BasketItemResponse> items, double totalBeforeDiscount) {}

