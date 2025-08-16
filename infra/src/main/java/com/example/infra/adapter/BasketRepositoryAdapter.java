package com.example.infra.adapter;

import com.example.application.provider.BasketRepository;
import com.example.domain.model.Basket;
import com.example.infra.entity.BasketEntity;
import com.example.infra.repository.BasketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryAdapter implements BasketRepository {

    private final BasketJpaRepository jpa;

    @Override
    public Optional<Basket> findOpenByUserId(String userId) {
        return jpa.findByUserId(userId)
                .map(e -> new Basket(e.getId(), e.getUserId()));
    }

    @Override
    public Basket save(Basket basket) {
        BasketEntity e = new BasketEntity();
        e.setId(basket.getId());  // If null, JPA will generate ID
        e.setUserId(basket.getUserId());

        // Save entity to DB
        BasketEntity saved = jpa.save(e);
        return new Basket(saved.getId(), saved.getUserId());
    }
}
