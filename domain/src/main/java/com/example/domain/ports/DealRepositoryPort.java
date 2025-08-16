package com.example.domain.ports;

import com.example.domain.model.Deal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DealRepositoryPort {
    Optional<Deal> findActiveByProductId(Long productId, LocalDateTime now);
    Deal save(Deal d);
    void deleteById(Long id);
    List<Deal> findAll(int page, int size);
    long count();
    List<Deal> findAllActive();
}
