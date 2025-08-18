package com.example.api.mapper;

import com.example.api.dto.AddToBasketRequest;
import com.example.api.dto.BasketItemResponse;
import com.example.api.dto.BasketViewResponse;
import com.example.application.dto.AddToBasketDto;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.BasketItemView;
import com.example.application.dto.BasketView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructCentralConfig.class)
public interface BasketApiMapper {

    // Vì AddToBasketDto cần userId nên cần custom method
    default AddToBasketDto toCommand(String customerId, AddToBasketRequest request) {
        return new AddToBasketDto(
                customerId,
                request.productId(),
                request.quantity()
        );
    }

    @Mapping(target = "name", source = "view.productName")
    BasketItemResponse toResponse(BasketItemView view);

    BasketViewResponse toResponse(BasketView view);
}
