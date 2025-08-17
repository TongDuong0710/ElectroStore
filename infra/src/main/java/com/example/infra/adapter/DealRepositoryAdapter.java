package com.example.infra.adapter;

import com.example.application.provider.DealRepository;
import com.example.domain.model.Deal;
import com.example.infra.entity.DealEntity;
import com.example.infra.entity.ProductEntity;
import com.example.infra.mapper.DealMapper;
import com.example.infra.repository.DealJpaRepository;
import com.example.infra.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DealRepositoryAdapter implements DealRepository {

    private final DealJpaRepository dealJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final DealMapper dealMapper;

    @Override
    public Optional<Deal> findActiveByProductId(Long productId, LocalDateTime now) {
        return dealJpaRepository.findActiveByProductId(productId, now)
                .map(dealMapper::toDomain);
    }

    @Override
    public Deal save(Deal deal) {
        ProductEntity productEntity = productJpaRepository.findById(deal.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        DealEntity entity = new DealEntity();
        entity.setProduct(productEntity);
        entity.setDealType(deal.getDealType());
        entity.setDescription(deal.getDescription());
        entity.setExpirationDateTime(deal.getExpirationDateTime());

        DealEntity saved = dealJpaRepository.save(entity);
        return dealMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        dealJpaRepository.deleteById(id);
    }

    @Override
    public List<Deal> findAll(int page, int size) {
        return dealJpaRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(dealMapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return dealJpaRepository.count();
    }

    @Override
    public List<Deal> findAllActive() {
        return dealJpaRepository.findAllActive(LocalDateTime.now())
                .stream()
                .map(dealMapper::toDomain)
                .toList();
    }
}
