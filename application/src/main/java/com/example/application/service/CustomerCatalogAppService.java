package com.example.application.service;


import com.example.application.command.ProductFilter;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductSummaryDto;

public interface CustomerCatalogAppService {

    PageResult<ProductSummaryDto> listProducts(ProductFilter filter, int page, int size);
}

