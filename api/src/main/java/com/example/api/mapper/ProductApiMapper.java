package com.example.api.mapper;

import com.example.api.dto.ProductCreateRequest;
import com.example.api.dto.ProductFilterRequest;
import com.example.api.dto.ProductResponse;
import com.example.api.dto.base.PageResponse;
import com.example.application.command.ProductCreateCmd;
import com.example.application.command.ProductFilter;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.PageResult;
import com.example.application.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructCentralConfig.class)
public interface ProductApiMapper {

    ProductCreateCmd toCommand(ProductCreateRequest request);

    ProductFilter toFilter(ProductFilterRequest request);

    ProductResponse toResponse(ProductDto dto);

    default PageResponse<ProductResponse> toPageResponse(PageResult<ProductDto> page) {
        List<ProductResponse> content = page.content().stream()
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
