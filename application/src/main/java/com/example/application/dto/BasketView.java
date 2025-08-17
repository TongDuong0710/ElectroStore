package com.example.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketView {
    private String userId;
    private List<BasketItemView> items;
    private BigDecimal totalAmount;
}
