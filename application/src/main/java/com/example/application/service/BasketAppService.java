package com.example.application.service;


import com.example.application.command.AddToBasketCmd;
import com.example.application.command.RemoveFromBasketCmd;
import com.example.application.dto.BasketView;

public interface BasketAppService {

    void addToBasket(AddToBasketCmd cmd);

    void removeFromBasket(RemoveFromBasketCmd cmd);

    BasketView getBasket(String userId);
}
