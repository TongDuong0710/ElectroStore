package com.example.domain.ports;

import com.example.domain.model.Basket;

import java.util.Optional;

public interface BasketRepositoryPort {
    Optional<Basket> findOpenByUserId(String userId);
    Basket save(Basket basket);

}
