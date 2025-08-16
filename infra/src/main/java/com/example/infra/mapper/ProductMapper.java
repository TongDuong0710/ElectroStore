package com.example.infra.mapper;

import com.example.domain.model.Product;
import com.example.infra.configurations.MapStructCentralConfig;
import com.example.infra.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface ProductMapper {

    // Entity -> Domain
    Product toDomain(ProductEntity entity);

    // Domain -> new Entity (insert)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toEntity(Product domain);

    // Domain -> update Entity (update-in-place)
    @BeanMapping(ignoreByDefault = true)
    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "category", source = "category"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "available", source = "available")
    })
    void updateEntity(Product domain, @MappingTarget ProductEntity entity);
}
