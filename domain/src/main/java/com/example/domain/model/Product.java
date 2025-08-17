package com.example.domain.model;

import com.example.domain.exception.InsufficientStockException;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private int stock;
    private boolean available;

    public Product(Long id, String name, String category, BigDecimal price, int stock, boolean available) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name is required");
        if (price == null || price.signum() <= 0) throw new IllegalArgumentException("Price must be > 0");
        if (stock < 0) throw new IllegalArgumentException("Stock must be >= 0");
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.available = available;
        syncAvailability();
    }

    public void decrementStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        if (qty > stock) throw new InsufficientStockException("Insufficient stock");
        this.stock -= qty;
        syncAvailability();
    }

    public void incrementStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        this.stock += qty;
        syncAvailability();
    }

    private void syncAvailability() {
        this.available = (this.stock > 0);
    }
    public boolean hasSufficientStock(int qty) {
        return qty > 0 && stock >= qty;
    }
}
