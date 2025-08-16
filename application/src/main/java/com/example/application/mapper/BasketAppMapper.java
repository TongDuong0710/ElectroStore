package com.example.application.mapper;

import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.BasketItemView;
import com.example.application.dto.BasketView;
import com.example.domain.model.Basket;
import com.example.domain.model.BasketItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructCentralConfig.class)
public interface BasketAppMapper {

    BasketView toView(Basket basket);

    BasketItemView toItemView(BasketItem item);

    List<BasketItemView> toItemViews(List<BasketItem> items);
}
