package com.example.api.controller.admin;

import com.example.api.dto.ProductCreateRequest;
import com.example.api.dto.ProductFilterRequest;
import com.example.api.dto.ProductResponse;
import com.example.api.dto.base.BaseResponseApi;
import com.example.api.dto.base.PageResponse;
import com.example.api.mapper.ProductApiMapper;
import com.example.application.service.AdminProductAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Tag(name = "Admin - Products", description = "Admin APIs for product management")
public class AdminProductController {

    private final AdminProductAppService productService;
    private final ProductApiMapper mapper;

    @Operation(summary = "Create a new product")
    @PostMapping
    public BaseResponseApi<ProductResponse> createProduct(
            @RequestBody ProductCreateRequest request) {
        return BaseResponseApi.success(
                mapper.toResponse(productService.create(mapper.toCommand(request)))
        );
    }

    @Operation(summary = "Delete a product by ID")
    @DeleteMapping("/{productId}")
    public BaseResponseApi<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete") @PathVariable Long productId) {
        productService.delete(productId);
        return BaseResponseApi.success(null);
    }

    @Operation(summary = "List all products with optional filtering and pagination")
    @GetMapping
    public BaseResponseApi<PageResponse<ProductResponse>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        ProductFilterRequest request = new ProductFilterRequest(category, minPrice, maxPrice, true, name);
        var result = productService.listProducts(mapper.toFilter(request), page, size);
        return BaseResponseApi.success(mapper.toPageResponse(result));
    }
}
