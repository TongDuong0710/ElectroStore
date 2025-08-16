package com.example.infra.repository;

import com.example.infra.entity.BasketItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketItemJpaRepository  extends JpaRepository<BasketItemEntity, Long> {
    List<BasketItemEntity> findByBasketId(Long basketId);
    Optional<BasketItemEntity> findByBasketIdAndProductId(Long basketId, Long productId);
    void deleteByBasketIdAndProductId(Long basketId, Long productId);
}
