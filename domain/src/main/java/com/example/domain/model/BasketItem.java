package com.example.domain.model;

import lombok.Data;

@Data
public class BasketItem {
    private Long id;
    private Long basketId;
    private Long productId;
    private int quantity;

    public BasketItem(Long id, Long basketId, Long productId, int quantity) {
        if (basketId == null) throw new IllegalArgumentException("basketId required");
        if (productId == null) throw new IllegalArgumentException("productId required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.id = id;
        this.basketId = basketId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void addQuantity(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("delta must be > 0");
        this.quantity += delta;
    }
}

