package com.example.infra.mapper;

import com.example.domain.model.Basket;
import com.example.infra.configurations.MapStructCentralConfig;
import com.example.infra.entity.BasketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class, uses = { BasketItemMapper.class })
public interface BasketMapper {

    // Entity -> Domain (chỉ id + userId)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "productIds", ignore = true)
    @Mapping(target = "items", source = "items")
    Basket toDomain(BasketEntity entity);
}

