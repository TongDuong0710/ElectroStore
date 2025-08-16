package com.example.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Deal {
    private Long id;
    private Long productId;
    private DealType dealType;
    private String description;
    private LocalDateTime expirationDateTime;

    public Deal(Long id, Long productId, DealType dealType, String description, LocalDateTime expirationDateTime) {
        if (productId == null) throw new IllegalArgumentException("productId required");
        if (dealType == null) throw new IllegalArgumentException("dealType required");
        if (expirationDateTime == null) throw new IllegalArgumentException("expiration required");
        this.id = id;
        this.productId = productId;
        this.dealType = dealType;
        this.description = description;
        this.expirationDateTime = expirationDateTime;
    }

    public boolean isActiveAt(LocalDateTime at) {
        return expirationDateTime.isAfter(at);
    }
}
