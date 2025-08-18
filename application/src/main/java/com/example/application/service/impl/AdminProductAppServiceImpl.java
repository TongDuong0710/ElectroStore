package com.example.application.service.impl;

import com.example.application.dto.ProductCreateDto;
import com.example.application.dto.ProductFilter;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductDto;
import com.example.application.mapper.ProductAppMapper;
import com.example.application.mapper.ProductFilterMapper;
import com.example.application.provider.ProductRepository;
import com.example.application.service.AdminProductAppService;
import com.example.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductAppServiceImpl implements AdminProductAppService {

    private final ProductRepository productRepository;
    private final ProductAppMapper productMapper;
    private final ProductFilterMapper productFilterMapper;

    @Override
    public ProductDto create(ProductCreateDto cmd) {
        // Map DTO to domain
        Product product = productMapper.toDomain(cmd);
        Product savedProduct = productRepository.save(product); // persist
        return productMapper.toDto(savedProduct);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);

    }

    @Override
    public PageResult<ProductDto> listProducts(ProductFilter filter, int page, int size) {
        // Apply filtering + pagination
        List<Product> products = productRepository.findByFilter(productFilterMapper.toModel(filter), page, size);
        long total = productRepository.countByFilter(productFilterMapper.toModel(filter));
        int totalPages = (int) Math.ceil((double) total / size);
        return new PageResult<>(
                products.stream().map(productMapper::toDto).toList(),
                total, totalPages, page, size
        );
    }

    @Override
    public List<ProductDto> listAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }
}

