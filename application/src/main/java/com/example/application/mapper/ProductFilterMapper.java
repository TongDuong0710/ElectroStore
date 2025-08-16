package com.example.application.mapper;

import com.example.application.command.ProductFilter;
import com.example.domain.model.ProductFilterModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductFilterMapper {

    ProductFilterMapper INSTANCE = Mappers.getMapper(ProductFilterMapper.class);

    ProductFilterModel toModel(ProductFilter command);
}
