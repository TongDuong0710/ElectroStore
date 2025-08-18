package com.example.domain.model;

import com.example.domain.exception.DomainException;
import com.example.domain.exception.ResponseCode;
import lombok.Data;

@Data
public class BasketItem {
    private Long id;
    private Long basketId;
    private Long productId;
    private int quantity;

    public BasketItem(Long id, Long basketId, Long productId, int quantity) {
        if (basketId == null) throw new DomainException(ResponseCode.INVALID_PARAM, "basketId required");
        if (productId == null) throw new DomainException(ResponseCode.INVALID_PARAM, "productId required");
        if (quantity <= 0) throw new DomainException(ResponseCode.INVALID_PARAM, "quantity must be > 0");
        this.id = id;
        this.basketId = basketId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void addQuantity(int delta) {
        if (delta <= 0) throw new DomainException(ResponseCode.INVALID_PARAM, "delta must be > 0");
        this.quantity += delta;
    }
}

