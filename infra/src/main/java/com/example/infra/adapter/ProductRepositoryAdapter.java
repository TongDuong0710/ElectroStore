package com.example.infra.adapter;

import com.example.application.provider.ProductRepository;
import com.example.domain.model.Product;
import com.example.domain.model.ProductFilterModel;
import com.example.infra.entity.ProductEntity;
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
    @Transactional
    public Product save(Product product) {
        if (product.getId() == null) {
            // INSERT
            ProductEntity e = mapper.toEntity(product); // version = null
            ProductEntity saved = jpa.save(e);
            return mapper.toDomain(saved);
        } else {
            // UPDATE: load managed entity to preserve version
            ProductEntity existing = jpa.findById(product.getId())
                    .orElseThrow(() -> new IllegalStateException("Product not found: " + product.getId()));

            // copy mutable fields from domain -> existing (đừng đụng version)
            existing.setName(product.getName());
            existing.setCategory(product.getCategory());
            existing.setPrice(product.getPrice());
            existing.setStock(product.getStock());
            existing.setAvailable(product.isAvailable());

            ProductEntity saved = jpa.save(existing); // managed; version OK
            return mapper.toDomain(saved);
        }
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
}
