package com.example.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BasketViewResponse {
    private List<BasketItemResponse> items;
    private BigDecimal totalAmount;
}
