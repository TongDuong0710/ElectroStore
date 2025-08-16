package com.example.domain.ports;

import com.example.domain.model.BasketItem;

import java.util.List;

public interface BasketItemRepositoryPort {
    List<BasketItem> findByBasketId(Long basketId);
    BasketItem save(BasketItem item);
    void deleteByBasketIdAndProductId(Long basketId, Long productId);
}
