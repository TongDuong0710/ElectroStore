package com.example.api.dto;

import com.example.domain.model.DealType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DealCreateRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Deal type is required")
    private DealType dealType;

    // Temporarily allow past deals for testing
    // @NotNull(message = "Expiration time is required")
    // @Future(message = "Expiration time must be in the future")
    private LocalDateTime expirationDateTime;
}

