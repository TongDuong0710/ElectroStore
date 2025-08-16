package com.example.infra.mapper;

import com.example.domain.model.Deal;
import com.example.infra.configurations.MapStructCentralConfig;
import com.example.infra.entity.DealEntity;
import com.example.infra.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(config = MapStructCentralConfig.class)
public interface DealMapper {
    // Entity -> Domain
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "expirationDateTime", expression = "java(toLocal(e.getExpirationDateTime()))")
    Deal toDomain(DealEntity e);

    // Domain -> new Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", expression = "java(refProduct(d.getProductId()))")
    @Mapping(target = "expirationDateTime", expression = "java(toOffset(d.getExpirationDateTime()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DealEntity toNewEntity(Deal d);
    // Helpers
    default ProductEntity refProduct(Long id) {
        if (id == null) return null;
        ProductEntity p = new ProductEntity();
        p.setId(id);
        return p;
    }
    default LocalDateTime toLocal(LocalDateTime odt) {
        return odt == null ? null : odt.toLocalDateTime();
    }
    default LocalDateTime toOffset(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atOffset(ZoneOffset.UTC);
    }
}
