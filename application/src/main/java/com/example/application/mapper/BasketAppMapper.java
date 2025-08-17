package com.example.application.mapper;

import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.BasketItemView;
import com.example.application.dto.BasketView;
import com.example.domain.model.Basket;
import com.example.domain.model.BasketItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface BasketAppMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "items", expression = "java(toItemViews(new java.util.ArrayList<>(basket.getItems())))")
    BasketView toView(Basket basket);

    BasketItemView toItemView(BasketItem item);

    default List<BasketItemView> toItemViews(List<BasketItem> items) {
        return items.stream().map(this::toItemView).collect(Collectors.toList());
    }
}

