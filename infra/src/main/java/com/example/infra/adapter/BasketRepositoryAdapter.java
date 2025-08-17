package com.example.infra.adapter;

import com.example.application.provider.BasketRepository;
import com.example.domain.model.Basket;
import com.example.infra.entity.BasketEntity;
import com.example.infra.mapper.BasketItemMapper;
import com.example.infra.mapper.BasketMapper;
import com.example.infra.repository.BasketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Basket save(Basket basket) {
        BasketEntity e = new BasketEntity();
        e.setId(basket.getId());  // If null, JPA will generate ID
        e.setUserId(basket.getUserId());
        e.setItems(basket.getItems().stream()
                .map(item -> basketItemMapper.toNewEntity(e.getId(), item))
                .toList());

        // Save entity to DB
        BasketEntity saved = jpa.save(e);
        return basketMapper.toDomain(saved);
    }
}
