package com.example.infra.mapper;

import com.example.domain.model.BasketItem;
import com.example.infra.configurations.MapStructCentralConfig;
import com.example.infra.entity.BasketEntity;
import com.example.infra.entity.BasketItemEntity;
import com.example.infra.entity.ProductEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructCentralConfig.class)
public interface BasketItemMapper {

    // Entity -> Domain
    @Mapping(target = "productId", source = "product.id")
    BasketItem toDomain(BasketItemEntity entity);

    // Domain -> new Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "basket", expression = "java(refBasket(basketId))")
    @Mapping(target = "product", expression = "java(refProduct(item.getProductId()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BasketItemEntity toNewEntity(@Context Long basketId, BasketItem item);

    // Helpers
    default BasketEntity refBasket(Long id) {
        if (id == null) return null;
        BasketEntity b = new BasketEntity();
        b.setId(id);
        return b;
    }
    default ProductEntity refProduct(Long id) {
        if (id == null) return null;
        ProductEntity p = new ProductEntity();
        p.setId(id);
        return p;
    }
}
