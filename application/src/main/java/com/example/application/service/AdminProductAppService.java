package com.example.application.service;


import com.example.application.dto.ProductCreateDto;
import com.example.application.dto.ProductFilter;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductDto;

public interface AdminProductAppService {

    ProductDto create(ProductCreateDto cmd);

    void delete(Long productId);

    PageResult<ProductDto> listProducts(ProductFilter filter, int page, int size);
}
