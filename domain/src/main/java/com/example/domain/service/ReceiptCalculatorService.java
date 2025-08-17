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
     * 1) App layer cung cấp: basket items, map productId->Product, map productId->active Deal (nếu có)
     * 2) Domain calc: line-by-line, apply deal theo rule B1G50_2ND
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
            if (deal != null && deal.isActiveAt(now) && deal.getDealType() == DealType.B1G50_2ND) {
                // B1G50_2ND: each pair (2 items) → one at 50% off
                int pairs = qty / 2;
                BigDecimal halfPrice = unit.multiply(new BigDecimal("0.5"));
                discount = halfPrice.multiply(BigDecimal.valueOf(pairs));
            }

            discount = discount.setScale(2, RoundingMode.HALF_UP);
            BigDecimal finalSubtotal = original.subtract(discount);

            lines.add(new ReceiptItem(
                    p.getId(), p.getName(), qty, unit, original, discount, finalSubtotal
            ));
            totalOriginal = totalOriginal.add(original);
            totalDiscount = totalDiscount.add(discount);
        }

        BigDecimal totalFinal = totalOriginal.subtract(totalDiscount).setScale(2, RoundingMode.HALF_UP);
        return new Receipt(List.copyOf(lines),
                totalOriginal.setScale(2, RoundingMode.HALF_UP),
                totalDiscount.setScale(2, RoundingMode.HALF_UP),
                totalFinal);
    }
}
