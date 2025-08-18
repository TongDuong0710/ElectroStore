package com.example.domain.model;

import com.example.domain.exception.DomainException;
import com.example.domain.exception.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class Deal {
    private Long id;
    private Long productId;
    private DealType dealType;
    private String description;
    private LocalDateTime expirationDateTime;

    public Deal(Long id, Long productId, DealType dealType, String description, LocalDateTime expirationDateTime) {
        if (productId == null) throw new DomainException(ResponseCode.INVALID_PARAM, "productId required");
        if (dealType == null) throw new DomainException(ResponseCode.INVALID_PARAM, "dealType required");
        if (expirationDateTime == null) throw new DomainException(ResponseCode.INVALID_PARAM, "expiration required");
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
