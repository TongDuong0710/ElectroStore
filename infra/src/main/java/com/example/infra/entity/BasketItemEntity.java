package com.example.infra.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "basket_item",
        uniqueConstraints = @UniqueConstraint(name="uk_basket_product", columnNames = {"basket_id","product_id"}),
        indexes = {
                @Index(name = "idx_basket_item_basket_id", columnList = "basket_id"),
                @Index(name = "idx_basket_item_product_id", columnList = "product_id")
        })
@Data
public class BasketItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private BasketEntity basket;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    void onUpdate() { this.updatedAt = LocalDateTime.now(); }

}

