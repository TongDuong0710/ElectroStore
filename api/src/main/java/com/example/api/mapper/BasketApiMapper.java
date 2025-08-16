package com.example.api.mapper;

import com.example.api.dto.AddToBasketRequest;
import com.example.api.dto.BasketItemResponse;
import com.example.api.dto.BasketViewResponse;
import com.example.api.dto.RemoveFromBasketRequest;
import com.example.application.command.AddToBasketCmd;
import com.example.application.command.RemoveFromBasketCmd;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.BasketItemView;
import com.example.application.dto.BasketView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface BasketApiMapper {

    // Vì AddToBasketCmd cần userId nên cần custom method
    default AddToBasketCmd toCommand(String customerId, AddToBasketRequest request) {
        return new AddToBasketCmd(
                customerId,
                request.productId(),
                request.quantity()
        );
    }

    default RemoveFromBasketCmd toCommand(String customerId, RemoveFromBasketRequest request) {
        return new RemoveFromBasketCmd(
                customerId,
                request.productId()
        );
    }

    BasketItemResponse toResponse(BasketItemView view);

    BasketViewResponse toResponse(BasketView view);
}
