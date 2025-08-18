package com.example.application.mapper;

import com.example.application.dto.ProductCreateDto;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.ProductDto;
import com.example.application.dto.ProductSummaryDto;
import com.example.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface ProductAppMapper {

    Product toDomain(ProductCreateDto cmd);

    ProductDto toDto(Product product);

    ProductSummaryDto toSummaryDto(Product product);
}
