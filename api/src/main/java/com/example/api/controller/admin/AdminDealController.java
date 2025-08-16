package com.example.api.controller.admin;

import com.example.api.dto.DealCreateRequest;
import com.example.api.dto.DealResponse;
import com.example.api.dto.base.BaseResponseApi;
import com.example.api.dto.base.PageResponse;
import com.example.api.mapper.DealApiMapper;
import com.example.application.service.AdminDealAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/deals")
@RequiredArgsConstructor
@Tag(name = "Admin - Deals", description = "Admin APIs for managing product deals")
public class AdminDealController {

    private final AdminDealAppService dealService;
    private final DealApiMapper mapper;

    @Operation(summary = "Create a new deal for a product")
    @PostMapping
    public BaseResponseApi<Void> createDeal(@RequestBody DealCreateRequest request) {
        dealService.create(mapper.toCommand(request));
        return BaseResponseApi.success(null);
    }

    @Operation(summary = "Delete an existing deal by ID")
    @DeleteMapping("/{dealId}")
    public BaseResponseApi<Void> deleteDeal(@PathVariable Long dealId) {
        dealService.delete(dealId);
        return BaseResponseApi.success(null);
    }

    @Operation(summary = "List all deals with pagination")
    @GetMapping
    public BaseResponseApi<PageResponse<DealResponse>> listDeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var result = dealService.listDeals(page, size);
        return BaseResponseApi.success(mapper.toPageResponse(result));
    }
}
