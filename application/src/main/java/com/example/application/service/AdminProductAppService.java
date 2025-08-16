package com.example.application.service;


import com.example.application.command.ProductCreateCmd;
import com.example.application.command.ProductFilter;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductDto;

public interface AdminProductAppService {

    ProductDto create(ProductCreateCmd cmd);

    void delete(Long productId);

    PageResult<ProductDto> listProducts(ProductFilter filter, int page, int size);
}
