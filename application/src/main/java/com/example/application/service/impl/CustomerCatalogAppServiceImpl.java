package com.example.application.service.impl;

import com.example.application.command.ProductFilter;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductSummaryDto;
import com.example.application.mapper.ProductAppMapper;
import com.example.application.mapper.ProductFilterMapper;
import com.example.application.provider.ProductRepository;
import com.example.application.service.CustomerCatalogAppService;
import com.example.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerCatalogAppServiceImpl implements CustomerCatalogAppService {

    private final ProductRepository productRepository;
    private final ProductAppMapper productMapper;
    private final ProductFilterMapper productFilterMapper;

    @Override
    public PageResult<ProductSummaryDto> listProducts(ProductFilter filter, int page, int size) {
        List<Product> products = productRepository.findByFilter(productFilterMapper.toModel(filter), page, size);
        long total = productRepository.countByFilter(productFilterMapper.toModel(filter));
        int totalPages = (int) Math.ceil((double) total / size);
        return new PageResult<>(
                products.stream().map(productMapper::toSummaryDto).toList(),
                total, totalPages, page, size
        );
    }
}
