package com.example.infra.adapter;

import com.example.application.provider.ProductRepository;
import com.example.domain.model.Product;
import com.example.domain.model.ProductFilterModel;
import com.example.infra.mapper.ProductMapper;
import com.example.infra.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository jpa;
    private final ProductMapper mapper;

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Product save(Product p) {
        var entity = mapper.toEntity(p);
        // preserve ID if updating
        entity.setId(p.getId());
        var saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Product> findByFilter(ProductFilterModel filter, int page, int size) {
        var pr = PageRequest.of(page, size);
        return jpa.findByFilter(
                        filter.category(),
                        filter.available(),
                        filter.minPrice(),
                        filter.maxPrice(),
                        pr)
                .map(mapper::toDomain)
                .getContent();
    }

    @Override
    public long countByFilter(ProductFilterModel filter) {
        return jpa.countByFilter(
                filter.category(),
                filter.available(),
                filter.minPrice(),
                filter.maxPrice());
    }

    @Override
    public List<Product> findAllByIds(List<Long> productIds) {
        return jpa.findAllById(productIds).stream()
                .map(mapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public boolean tryDecrementStock(Long productId, int qty) {
        return jpa.tryDecrementStock(productId, qty) > 0;
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
