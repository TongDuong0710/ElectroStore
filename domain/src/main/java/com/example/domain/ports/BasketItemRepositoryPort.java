package com.example.domain.ports;

import com.example.domain.model.BasketItem;

import java.util.Optional;

public interface BasketItemRepositoryPort {
    Optional<BasketItem> findByBasketAndProduct(Long productId, Long basketId);
    BasketItem save(BasketItem item);
    void deleteByBasketIdAndProductId(Long basketId, Long productId);
}
