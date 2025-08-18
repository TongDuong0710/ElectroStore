package com.example.infra.repository;

import com.example.infra.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
       select p
       from ProductEntity p
       where (:category is null or :category = '' or p.category like %:category%)
         and (:available is null or p.available = :available)
         and (:minPrice is null or p.price >= :minPrice)
         and (:maxPrice is null or p.price <= :maxPrice)
       order by p.createdAt desc, p.id desc
       """)
    Page<ProductEntity> findByFilter(@Param("category") String category,
                                     @Param("available") Boolean available,
                                     @Param("minPrice") BigDecimal priceMin,
                                     @Param("maxPrice") BigDecimal priceMax,
                                     Pageable pageable);

    @Query("""
       select count(p)
       from ProductEntity p
       where (:category is null or :category = '' or p.category like %:category%)
         and (:available is null or p.available = :available)
         and (:minPrice is null or p.price >= :minPrice)
         and (:maxPrice is null or p.price <= :maxPrice)
       """)
    long countByFilter(@Param("category") String category,
                       @Param("available") Boolean available,
                       @Param("minPrice") BigDecimal priceMin,
                       @Param("maxPrice") BigDecimal priceMax);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
            value = """
    UPDATE product
    SET stock = stock - :qty,
        available = CASE WHEN (stock - :qty) > 0 THEN true ELSE false END
    WHERE id = :id AND stock >= :qty
    """,
            nativeQuery = true
    )
    int tryDecrementStock(@Param("id") Long productId, @Param("qty") int qty);

}
