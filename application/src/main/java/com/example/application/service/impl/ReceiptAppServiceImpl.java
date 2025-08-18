package com.example.application.service.impl;


import com.example.application.dto.ReceiptDto;
import com.example.application.exceptions.AppResponseCode;
import com.example.application.exceptions.ApplicationException;
import com.example.application.mapper.ReceiptAppMapper;
import com.example.application.provider.BasketRepository;
import com.example.application.provider.DealRepository;
import com.example.application.provider.ProductRepository;
import com.example.application.service.ReceiptAppService;
import com.example.domain.model.Deal;
import com.example.domain.model.Product;
import com.example.domain.service.ReceiptCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceiptAppServiceImpl implements ReceiptAppService {
    private final BasketRepository basketRepo;
    private final ProductRepository productRepo;
    private final DealRepository dealRepo;
    private final ReceiptCalculatorService calculator;
    private final ReceiptAppMapper mapper;

    @Override
    public ReceiptDto generateReceipt(String userId) {
        var basket = basketRepo.findOpenByUserId(userId)
                .orElseThrow(() ->new ApplicationException(AppResponseCode.NOT_FOUND, "Basket not found"));

        // Fetch product list
        var productList = productRepo.findAllByIds(basket.getProductIds());
        var productMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Fetch all active deals
        var activeDealList = dealRepo.findAllActive();
        var dealMap = activeDealList.stream()
                .collect(Collectors.toMap(Deal::getProductId, Function.identity()));

        var receipt = calculator.calculate(basket.getItems(), productMap, dealMap, LocalDateTime.now());

        return mapper.toDto(receipt);
    }
}
