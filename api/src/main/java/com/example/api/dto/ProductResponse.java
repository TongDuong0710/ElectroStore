package com.example.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private int stock;
    private boolean available;
}
