package com.example.domain.service;

import com.example.domain.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReceiptCalculatorService {

    /** FLOW:
     * 1) The Application layer provides: basket items, a map of productId to Product, and a map of productId to active Deal (if any)
     * 2) Domain calculation: process each line item, apply the deal based on the B1G50_2ND rule
     */
    public Receipt calculate(Collection<BasketItem> basketItems,
                             Map<Long, Product> products,
                             Map<Long, Deal> activeDeals,
                             LocalDateTime now) {

        List<ReceiptItem> lines = new ArrayList<>();
        BigDecimal totalOriginal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (BasketItem bi : basketItems) {
            Product p = products.get(bi.getProductId());
            if (p == null) throw new IllegalStateException("Product missing for id=" + bi.getProductId());

            BigDecimal unit = p.getPrice();
            int qty = bi.getQuantity();
            BigDecimal original = unit.multiply(BigDecimal.valueOf(qty));

            BigDecimal discount = BigDecimal.ZERO;
            Deal deal = activeDeals.get(p.getId());

            if (deal != null && deal.isActiveAt(now)) {
                // Calculate discount based on the deal type
                discount = calculateDiscountByDeal(deal, unit, qty);
            }

            discount = discount.setScale(2, RoundingMode.HALF_UP);
            BigDecimal finalSubtotal = original.subtract(discount);

            lines.add(new ReceiptItem(
                    p.getId(),
                    p.getName(),
                    qty,
                    unit,
                    original,
                    discount,
                    finalSubtotal,
                    discount.compareTo(BigDecimal.ZERO) > 0 // appliedDeal: true if discount > 0
            ));

            totalOriginal = totalOriginal.add(original);
            totalDiscount = totalDiscount.add(discount);
        }

        BigDecimal totalFinal = totalOriginal.subtract(totalDiscount).setScale(2, RoundingMode.HALF_UP);

        return new Receipt(
                List.copyOf(lines),
                totalOriginal.setScale(2, RoundingMode.HALF_UP),
                totalDiscount.setScale(2, RoundingMode.HALF_UP),
                totalFinal
        );
    }
    private BigDecimal calculateDiscountByDeal(Deal deal, BigDecimal unitPrice, int quantity) {
        return switch (deal.getDealType()) {
            case B1G50_2ND -> calculateB1G50Discount(unitPrice, quantity);
            case THIRTY_PERCENT_OFF -> calculatePercentOffDiscount(unitPrice, quantity, new BigDecimal("0.30"));
            // case ... other deal types
            default -> BigDecimal.ZERO;
        };
    }

    private BigDecimal calculateB1G50Discount(BigDecimal unitPrice, int quantity) {
        int pairs = quantity / 2;
        BigDecimal halfPrice = unitPrice.multiply(new BigDecimal("0.5"));
        return halfPrice.multiply(BigDecimal.valueOf(pairs));
    }

    private BigDecimal calculatePercentOffDiscount(BigDecimal unitPrice, int quantity, BigDecimal percent) {
        BigDecimal original = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return original.multiply(percent);
    }
}
