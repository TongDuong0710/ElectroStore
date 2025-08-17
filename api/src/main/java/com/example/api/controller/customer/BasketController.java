package com.example.api.controller.customer;

import com.example.api.dto.AddToBasketRequest;
import com.example.api.dto.BasketViewResponse;
import com.example.api.dto.base.BaseResponseApi;
import com.example.api.mapper.BasketApiMapper;
import com.example.application.command.RemoveFromBasketCmd;
import com.example.application.service.BasketAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/basket")
@RequiredArgsConstructor
@Tag(name = "Customer - Basket", description = "Customer APIs for managing shopping basket")
public class BasketController {

    private final BasketAppService basketAppService;
    private final BasketApiMapper basketApiMapper;

    // TODO: Replace this with actual user context extraction in real implementation
    private static final String defaultCustomerId = "cus-123";

    @Operation(summary = "Add product to basket (and reduce stock)")
    @PostMapping("/items")
    public BaseResponseApi<Void> addToBasket(
            @RequestHeader(value = "X-Customer-ID", required = false) String customerId,
            @RequestBody AddToBasketRequest request) {
        String finalCustomerId = StringUtils.isNoneBlank(customerId) ? customerId : defaultCustomerId;
        var cmd = basketApiMapper.toCommand(finalCustomerId, request);
        basketAppService.addToBasket(cmd);
        return BaseResponseApi.success(null);
    }
    @Operation(summary = "Delete product from basket")
    @DeleteMapping("/items/{productId}")
    public BaseResponseApi<Void> removeFromBasket(
            @RequestHeader(value = "X-Customer-ID", required = false) String customerId,
            @PathVariable Long productId) {

        var cmd = new RemoveFromBasketCmd(customerId, productId);
        basketAppService.removeFromBasket(cmd);
        return BaseResponseApi.success(null);
    }

    @Operation(summary = "View current basket")
    @GetMapping
    public BaseResponseApi<BasketViewResponse> getBasket(
            @RequestHeader(value = "X-Customer-ID", required = false) String customerId) {
        String finalCustomerId = StringUtils.isNoneBlank(customerId) ? customerId : defaultCustomerId;
        var result = basketAppService.getBasket(finalCustomerId);
        return BaseResponseApi.success(basketApiMapper.toResponse(result));
    }
}
