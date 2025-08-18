package com.example.application.mapper;

import com.example.application.dto.ProductFilter;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.domain.model.ProductFilterModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface ProductFilterMapper {

    ProductFilterMapper INSTANCE = Mappers.getMapper(ProductFilterMapper.class);

    ProductFilterModel toModel(ProductFilter command);
}
