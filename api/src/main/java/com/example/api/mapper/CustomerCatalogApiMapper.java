package com.example.api.mapper;

import com.example.api.dto.ProductFilterRequest;
import com.example.api.dto.ProductSummaryResponse;
import com.example.api.dto.base.PageResponse;
import com.example.application.command.ProductFilter;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductSummaryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructCentralConfig.class)
public interface CustomerCatalogApiMapper {

    ProductFilter toFilter(ProductFilterRequest request);
    ProductSummaryResponse toResponse(ProductSummaryDto dto);
    default PageResponse<ProductSummaryResponse> toSummaryPageResponse(PageResult<ProductSummaryDto> page) {
        List<ProductSummaryResponse> content = page.content().stream()
                .map(this::toResponse)
                .toList();
        return new PageResponse<>(
                content,
                page.page(),
                page.size(),
                page.totalElements()
        );
    }
}
