package com.example.infra.repository;

import com.example.infra.entity.BasketItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketItemJpaRepository  extends JpaRepository<BasketItemEntity, Long> {
    Optional<BasketItemEntity> findByBasketIdAndProductId(Long basketId, Long productId);
    void deleteByBasketIdAndProductId(Long basketId, Long productId);
}
