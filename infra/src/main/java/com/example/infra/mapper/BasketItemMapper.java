package com.example.infra.mapper;

import com.example.domain.model.BasketItem;
import com.example.infra.configurations.MapStructCentralConfig;
import com.example.infra.entity.BasketEntity;
import com.example.infra.entity.BasketItemEntity;
import com.example.infra.entity.ProductEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface BasketItemMapper {

    // Entity -> Domain
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "basketId", source = "basketId")
    BasketItem toDomain(BasketItemEntity entity);

    List<BasketItem> toDomain(List<BasketItemEntity> entities);

    default Map<Long, BasketItem> map(List<BasketItemEntity> items) {
        if (items == null) return new LinkedHashMap<>();
        return items.stream()
                .map(this::toDomain)
                .collect(Collectors.toMap(
                        BasketItem::getProductId,  // key
                        Function.identity(),       // value
                        (a, b) -> b,               // merge function if duplicate
                        LinkedHashMap::new         // preserve order
                ));
    }

    // Domain -> new Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "basketId", source = "basketId")
    @Mapping(target = "product", expression = "java(refProduct(item.getProductId()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BasketItemEntity toNewEntity(@Context Long basketId, BasketItem item);


    // Domain -> new Entity
    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "basketId", source = "basketId")
    @Mapping(target = "product", expression = "java(refProduct(item.getProductId()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BasketItemEntity toEntity(@Context Long basketId, BasketItem item);

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
