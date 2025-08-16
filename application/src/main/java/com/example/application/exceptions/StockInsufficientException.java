package com.example.application.exceptions;

import com.example.domain.exception.BusinessException;

/**
 * Thrown when trying to add more items to basket than stock allows.
 */
public class StockInsufficientException extends BusinessException {
    public StockInsufficientException(Long productId, int requested, int available) {
        super("Insufficient stock for product " + productId +
                ". Requested: " + requested + ", Available: " + available);
    }
}
