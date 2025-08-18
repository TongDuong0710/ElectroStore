package com.example.application.service;


import com.example.application.dto.PageResult;
import com.example.application.dto.ProductCreateDto;
import com.example.application.dto.ProductDto;
import com.example.application.dto.ProductFilter;

import java.util.List;

public interface AdminProductAppService {

    ProductDto create(ProductCreateDto cmd);

    void delete(Long productId);

    PageResult<ProductDto> listProducts(ProductFilter filter, int page, int size);
    List<ProductDto> listAllProducts();

}
