package com.example.infra.adapter;

import com.example.application.provider.BasketRepository;
import com.example.domain.model.Basket;
import com.example.infra.entity.BasketEntity;
import com.example.infra.entity.BasketItemEntity;
import com.example.infra.mapper.BasketItemMapper;
import com.example.infra.mapper.BasketMapper;
import com.example.infra.repository.BasketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryAdapter implements BasketRepository {
    private final BasketJpaRepository jpa;
    private final BasketMapper basketMapper;
    private final BasketItemMapper basketItemMapper;

    @Override
    public Optional<Basket> findOpenByUserId(String userId) {
        return jpa.findByUserId(userId).map(basketMapper::toDomain);
    }

    @Override
    @Transactional
    public Basket save(Basket basket) {
        final BasketEntity e;

        if (basket.getId() == null) {
            // Create new owning entity
            e = new BasketEntity();
            e.setUserId(basket.getUserId());
            e.setItems(new ArrayList<>()); // initialize once
        } else {
            // IMPORTANT: load a managed instance; do NOT new + setId
            e = jpa.findById(basket.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Basket not found: " + basket.getId()));
            e.setUserId(basket.getUserId());

            // IMPORTANT: mutate the managed collection in-place (do not e.setItems(newList))
            e.getItems().clear();
        }

        // Map incoming items -> entities (set basketId / relationships as needed)
        List<BasketItemEntity> mapped = basket.getItems().stream()
                .map(item -> basketItemMapper.toEntity(e.getId(), item))
                .toList();

        // IMPORTANT: addAll on the existing managed collection
        e.getItems().addAll(mapped);

        BasketEntity saved = jpa.save(e);
        return basketMapper.toDomain(saved);
    }
}
