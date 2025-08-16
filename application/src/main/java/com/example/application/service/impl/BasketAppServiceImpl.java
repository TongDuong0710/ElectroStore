package com.example.application.service.impl;

import com.example.application.command.AddToBasketCmd;
import com.example.application.command.RemoveFromBasketCmd;
import com.example.application.dto.BasketView;
import com.example.application.mapper.BasketAppMapper;
import com.example.application.provider.BasketRepository;
import com.example.application.provider.ProductRepository;
import com.example.application.service.BasketAppService;
import com.example.domain.model.Basket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketAppServiceImpl implements BasketAppService {

    private final BasketRepository basketRepo;
    private final ProductRepository productRepo;
    private final BasketAppMapper basketMapper;

    @Override
    @Transactional
    public void addToBasket(AddToBasketCmd cmd) {
        Basket basket = basketRepo.findOpenByUserId(cmd.userId())
                .orElseGet(() -> new Basket(null, cmd.userId())); // Let repository generate ID

        var product = productRepo.findById(cmd.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Add product to basket using domain logic (handles merging quantity)
        basket.addOrIncrease(product.getId(), cmd.quantity());

        // Decrease product stock after adding to basket
        product.decrementStock(cmd.quantity());

        basketRepo.save(basket);
        productRepo.save(product);
    }

    @Override
    @Transactional
    public void removeFromBasket(RemoveFromBasketCmd cmd) {
        Basket basket = basketRepo.findOpenByUserId(cmd.userId())
                .orElseThrow(() -> new IllegalArgumentException("Basket not found"));
        basket.removeProduct(cmd.productId());

        // Optionally restore stock if needed (your business logic)
        basketRepo.save(basket);
    }

    @Override
    public BasketView getBasket(String userId) {
        return basketRepo.findOpenByUserId(userId)
                .map(basketMapper::toView)
                .orElse(new BasketView(userId, java.util.Collections.emptyList()));
    }
}
