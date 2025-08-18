package com.example.infra.mapper;

import com.example.domain.model.BasketItem;
import com.example.infra.configurations.MapStructCentralConfig;
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
    @Mapping(target = "product", expression = "java(getProductRef(item.getProductId(), em))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BasketItemEntity toNewEntity(@Context Long basketId,
                                 @Context jakarta.persistence.EntityManager em,
                                 BasketItem item);

    // helper: lấy managed reference, KHÔNG tạo entity mới
    default ProductEntity getProductRef(Long id, @Context jakarta.persistence.EntityManager em) {
        return (id == null) ? null : em.getReference(ProductEntity.class, id);
    }
}
