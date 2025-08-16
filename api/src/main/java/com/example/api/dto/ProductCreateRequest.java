package com.example.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private int stock;
}
