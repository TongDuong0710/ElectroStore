package com.example.infra.entity;

import com.example.domain.model.DealType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "deal",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_product_deal_type", columnNames = {"product_id", "deal_type"})
        },
        indexes = {
                @Index(name = "idx_deal_product_id", columnList = "product_id"),
                @Index(name = "idx_deal_expiration_datetime", columnList = "expiration_datetime"),
                @Index(name = "idx_deal_created_at_id", columnList = "created_at, id")
        }
)
@Data
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false, length = 50)
    private DealType dealType;

    @Column(length = 255)
    private String description;

    @Column(name = "expiration_datetime", nullable = false)
    private LocalDateTime expirationDateTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
