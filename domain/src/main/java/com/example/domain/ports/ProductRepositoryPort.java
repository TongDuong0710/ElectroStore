package com.example.domain.ports;


import com.example.domain.model.Product;
import com.example.domain.model.ProductFilterModel;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Optional<Product> findById(Long id);
    Product save(Product p);
    void deleteById(Long id);
    // queries for filtering/paging sẽ để application/infra hiện thực
    List<Product> findByFilter(ProductFilterModel filter, int page, int size);
    long countByFilter(ProductFilterModel filter);
    List<Product> findAllByIds(List<Long> productIds);
    boolean tryDecrementStock(Long productId, int qty);
    List<Product> findAll();

}
