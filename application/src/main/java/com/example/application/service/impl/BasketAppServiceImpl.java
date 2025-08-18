package com.example.application.service.impl;

import com.example.application.dto.AddToBasketDto;
import com.example.application.dto.RemoveFromBasketDto;
import com.example.application.dto.BasketItemView;
import com.example.application.dto.BasketView;
import com.example.application.exceptions.AppResponseCode;
import com.example.application.exceptions.ApplicationException;
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

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketAppServiceImpl implements BasketAppService {

    private final BasketRepository basketRepo;
    private final ProductRepository productRepo;
    private final BasketAppMapper basketMapper;

    @Override @Transactional
    public void addToBasket(AddToBasketDto cmd) {
        if (cmd.quantity() <= 0) throw new ApplicationException(AppResponseCode.INVALID_PARAM, "Quantity must be > 0");

        var basket = basketRepo.findOpenByUserId(cmd.userId())
                .orElseGet(() -> basketRepo.save(new Basket(null, cmd.userId(), null)));

        boolean ok = productRepo.tryDecrementStock(cmd.productId(), cmd.quantity());
        if (!ok) throw new ApplicationException(AppResponseCode.INSUFFICIENT_STOCK, "Insufficient stock"); // -> 409

        basket.addOrIncrease(new BasketItem(null, basket.getId(), cmd.productId(), cmd.quantity()));
        basketRepo.save(basket);
    }


    @Override
    @Transactional
    public void removeFromBasket(RemoveFromBasketDto cmd) {
        // Find user's open basket or fail
        Basket basket = basketRepo.findOpenByUserId(cmd.userId())
                .orElseThrow(() -> new ApplicationException(AppResponseCode.NOT_FOUND, "Basket not found"));

        // Get item from basket or fail if not found
        BasketItem item = basket.getItems().stream()
                .filter(i -> i.getProductId().equals(cmd.productId()))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(AppResponseCode.NOT_FOUND, "Product not found in basket"));

        // Restore stock to product
        Product product = productRepo.findById(cmd.productId())
                .orElseThrow(() -> new ApplicationException(AppResponseCode.NOT_FOUND, "Product not found"));
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
                    var productList = productRepo.findAllByIds(basket.getProductIds());
                    Map<Long, Product> productMap = productList.stream()
                            .collect(Collectors.toMap(Product::getId, Function.identity()));

                    var itemViews = basket.getItems().stream()
                            .map(item -> {
                                Product product = productMap.get(item.getProductId());
                                String productName = product != null ? product.getName() : null;
                                BigDecimal unitPrice = product != null ? product.getPrice() : BigDecimal.ZERO;
                                return new BasketItemView(
                                        item.getProductId(),
                                        productName,
                                        item.getQuantity(),
                                        unitPrice
                                );
                            })
                            .toList();

                    var total = basket.calculateTotal(productMap);

                    return new BasketView(basket.getUserId(), itemViews, total);
                })
                .orElse(null);
    }
}
