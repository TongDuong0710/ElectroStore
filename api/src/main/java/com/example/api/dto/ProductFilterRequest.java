package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductFilterRequest {
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean available;
    private String name;
}

