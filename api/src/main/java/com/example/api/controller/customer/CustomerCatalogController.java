package com.example.api.controller.customer;

import com.example.api.dto.ProductFilterRequest;
import com.example.api.dto.ProductSummaryResponse;
import com.example.api.dto.base.BaseResponseApi;
import com.example.api.dto.base.PageResponse;
import com.example.api.mapper.CustomerCatalogApiMapper;
import com.example.application.service.CustomerCatalogAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/customer/products")
@RequiredArgsConstructor
@Tag(name = "Customer - Product Catalog", description = "Customer API to browse product catalog")
public class CustomerCatalogController {

    private final CustomerCatalogAppService catalogService;
    private final CustomerCatalogApiMapper mapper;

    @Operation(summary = "Get product catalog for customer", description = "List available products with optional filters and pagination")
    @GetMapping
    public BaseResponseApi<PageResponse<ProductSummaryResponse>> listProducts(
            @Parameter(description = "Search by product name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by product category") @RequestParam(required = false) String category,
            @Parameter(description = "Minimum price filter") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Filter by availability") @RequestParam(required = false, defaultValue = "true") Boolean available,
            @Parameter(description = "Page index (default 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (default 10)") @RequestParam(defaultValue = "10") int size) {

        ProductFilterRequest request = new ProductFilterRequest(category, minPrice, maxPrice, available, name);
        var filter = mapper.toFilter(request);
        var result = catalogService.listProducts(filter, page, size);
        return BaseResponseApi.success(mapper.toSummaryPageResponse(result));
    }
}
