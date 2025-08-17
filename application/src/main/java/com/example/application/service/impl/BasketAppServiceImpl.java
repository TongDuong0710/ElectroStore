package com.example.application.service.impl;

import com.example.application.command.AddToBasketCmd;
import com.example.application.command.RemoveFromBasketCmd;
import com.example.application.dto.BasketItemView;
import com.example.application.dto.BasketView;
import com.example.application.mapper.BasketAppMapper;
import com.example.application.provider.BasketRepository;
import com.example.application.provider.ProductRepository;
import com.example.application.service.BasketAppService;
import com.example.domain.model.Basket;
import com.example.domain.model.BasketItem;
import com.example.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasketAppServiceImpl implements BasketAppService {

    private final BasketRepository basketRepo;
    private final ProductRepository productRepo;
    private final BasketAppMapper basketMapper;

    @Override
    @Transactional
    public void addToBasket(AddToBasketCmd cmd) {
        // Get or create an open basket for the user
        var basket = basketRepo.findOpenByUserId(cmd.userId())
                .orElseGet(() -> basketRepo.save(new Basket(null, cmd.userId(), null)));

        // Find the product or fail if not found
        var product = productRepo.findById(cmd.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Add item to basket (merge if already exists)
        basket.addOrIncrease(new BasketItem(null,basket.getId(), product.getId(), cmd.quantity()));

        // Decrease product stock and save
        product.decrementStock(cmd.quantity());
        productRepo.save(product);

        // Save basket with updated items
        basketRepo.save(basket);
    }

    @Override
    @Transactional
    public void removeFromBasket(RemoveFromBasketCmd cmd) {
        // Find user's open basket or fail
        Basket basket = basketRepo.findOpenByUserId(cmd.userId())
                .orElseThrow(() -> new IllegalArgumentException("Basket not found"));

        // Get item from basket or fail if not found
        BasketItem item = basket.getItems().stream()
                .filter(i -> i.getProductId().equals(cmd.productId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in basket"));

        // Restore stock to product
        Product product = productRepo.findById(cmd.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.incrementStock(item.getQuantity());
        productRepo.save(product);

        // Remove item from basket and persist changes
        basket.removeProduct(cmd.productId());
        basketRepo.save(basket);
    }

    @Override
    public BasketView getBasket(String userId) {
        return basketRepo.findOpenByUserId(userId)
                .map(basket -> {
                    // Preload all products in the basket to avoid N+1 problem
                    Map<Long, Product> productMap = new HashMap<>();
                    basket.getItems().forEach(item -> {
                        if (!productMap.containsKey(item.getProductId())) {
                            // N+1 problem, i will fix it later when i have time
                            productRepo.findById(item.getProductId()).ifPresent(
                                    product -> productMap.put(product.getId(), product));
                        }
                    });

                    // Map items with product info
                    var itemViews = basket.getItems().stream()
                        .map(item -> {
                            Product product = productMap.get(item.getProductId());
                            String productName = product != null ? product.getName() : null;
                            java.math.BigDecimal unitPrice = product != null ? product.getPrice() : java.math.BigDecimal.ZERO;
                            return new BasketItemView(
                                    item.getProductId(),
                                    productName,
                                    item.getQuantity(),
                                    unitPrice
                            );
                        })
                        .toList();

                    // Calculate totalAmount
                    var total = itemViews.stream()
                        .map(iv -> iv.getUnitPrice().multiply(java.math.BigDecimal.valueOf(iv.getQuantity())))
                        .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

                    return new BasketView(basket.getUserId(), itemViews, total);
                })
                .orElse(null);
    }
}
