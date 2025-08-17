package com.example.api.controller.customer;

import com.example.api.dto.ReceiptResponse;
import com.example.api.dto.base.BaseResponseApi;
import com.example.api.mapper.ReceiptApiMapper;
import com.example.application.service.ReceiptAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/receipt")
@RequiredArgsConstructor
@Tag(name = "Customer - Receipt", description = "Customer API for generating and viewing receipt")
public class ReceiptController {

    private final ReceiptAppService receiptAppService;
    private final ReceiptApiMapper receiptApiMapper;

    @Operation(summary = "Generate receipt for current basket",
            description = "Return calculated total amount, applicable deals, and item breakdown for the given customer")
    @GetMapping
    public BaseResponseApi<ReceiptResponse> getReceipt(
            @RequestHeader(value = "X-Customer-ID", required = false) String customerId) {
        var result = receiptAppService.generateReceipt(customerId);
        return BaseResponseApi.success(receiptApiMapper.toResponse(result));
    }
}
