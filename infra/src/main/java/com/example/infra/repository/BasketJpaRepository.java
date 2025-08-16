package com.example.infra.repository;

import com.example.infra.entity.BasketEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketJpaRepository extends JpaRepository<BasketEntity, Long> {

    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<BasketEntity> findByUserId(String userId);
}
