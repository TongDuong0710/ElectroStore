package com.example.infra.mapper;

import com.example.domain.model.Basket;
import com.example.infra.configurations.MapStructCentralConfig;
import com.example.infra.entity.BasketEntity;
import com.example.infra.entity.BasketItemEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructCentralConfig.class, uses = { BasketItemMapper.class })
public interface BasketMapper {

    // Entity -> Domain (chỉ id + userId, items add ở @AfterMapping)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    Basket toDomain(BasketEntity entity);

    @AfterMapping
    default void fillItems(BasketEntity entity, @MappingTarget Basket target) {
        if (entity.getItems() == null) return;
        for (BasketItemEntity e : entity.getItems()) {
            Long productId = e.getProduct().getId();
            int qty = e.getQuantity();
            // FLOW: domain giữ Map<productId, BasketItem> → merge theo productId
            target.addOrIncrease(productId, qty);
        }
    }
}

