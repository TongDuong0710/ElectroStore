package com.example.infra.repository;

import com.example.infra.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DealJpaRepository extends JpaRepository<DealEntity, Long> {

    // Return the nearest-active deal for a product (by soonest expiration)
    @Query("""
           select d
           from DealEntity d
           where d.product.id = :productId
             and d.expirationDateTime > :now
           order by d.expirationDateTime asc
           """)
    Optional<DealEntity> findActiveByProductId(@Param("productId") Long productId,
                                               @Param("now") LocalDateTime now);

    // Return all active deals as of 'now'
    @Query("""
           select d
           from DealEntity d
           where d.expirationDateTime > :now
           order by d.expirationDateTime asc
           """)
    List<DealEntity> findAllActive(@Param("now") LocalDateTime now);
}
