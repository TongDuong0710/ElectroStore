package com.example.infra.repository;

import com.example.infra.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
           select p
           from ProductEntity p
           where (:category is null or p.category = :category)
             and (:available is null or p.available = :available)
             and (:priceMin is null or p.price >= :priceMin)
             and (:priceMax is null or p.price <= :priceMax)
           order by p.createdAt desc, p.id desc
           """)
    Page<ProductEntity> findByFilter(@Param("category") String category,
                                     @Param("available") Boolean available,
                                     @Param("priceMin") BigDecimal priceMin,
                                     @Param("priceMax") BigDecimal priceMax,
                                     Pageable pageable);

    @Query("""
           select count(p)
           from ProductEntity p
           where (:category is null or p.category = :category)
             and (:available is null or p.available = :available)
             and (:priceMin is null or p.price >= :priceMin)
             and (:priceMax is null or p.price <= :priceMax)
           """)
    long countByFilter(@Param("category") String category,
                       @Param("available") Boolean available,
                       @Param("priceMin") BigDecimal priceMin,
                       @Param("priceMax") BigDecimal priceMax);
}
