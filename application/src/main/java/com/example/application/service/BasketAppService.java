package com.example.application.service;


import com.example.application.dto.AddToBasketDto;
import com.example.application.dto.RemoveFromBasketDto;
import com.example.application.dto.BasketView;

public interface BasketAppService {

    void addToBasket(AddToBasketDto cmd);

    void removeFromBasket(RemoveFromBasketDto cmd);

    BasketView getBasket(String userId);
}
