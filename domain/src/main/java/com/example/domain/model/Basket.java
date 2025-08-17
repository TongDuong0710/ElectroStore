package com.example.domain.model;

import lombok.Data;

import java.util.*;
@Data
public class Basket {
    private Long id;
    private String userId;
    // key = productId for quick merge/update
    private final Map<Long, BasketItem> items = new LinkedHashMap<>();

    public Basket(Long id, String userId, Map<Long, BasketItem> items) {
        if (userId == null || userId.isBlank()) throw new IllegalArgumentException("userId required");
        this.id = id;
        this.userId = userId;
        if (items != null) {
            this.items.putAll(items);
        }
    }

    /** FLOW: add item → merge by productId (domain-level, stock decrease happens in application using StockDomainService) */
    public void addOrIncrease(BasketItem basketItem) {
        BasketItem existing = items.get(basketItem.getProductId());
        if (existing == null) {
            items.put(basketItem.getProductId(), basketItem);
        } else {
            existing.addQuantity(basketItem.getQuantity());
        }
    }

    /** FLOW: remove item entirely (application will restore stock accordingly) */
    public void removeProduct(Long productId) {
        if (!items.containsKey(productId)) throw new NoSuchElementException("Item not found in basket");
        items.remove(productId);
    }

    public Collection<BasketItem> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    /** Returns a list of product IDs currently in the basket. */
    public List<Long> getProductIds() {
        return new ArrayList<>(items.keySet());
    }

}
