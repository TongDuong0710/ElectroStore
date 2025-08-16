package com.example.domain.model;

import lombok.Data;

import java.util.*;
@Data
public class Basket {
    private Long id;
    private String userId;
    // key = productId for quick merge/update
    private final Map<Long, BasketItem> items = new LinkedHashMap<>();

    public Basket(Long id, String userId) {
        if (userId == null || userId.isBlank()) throw new IllegalArgumentException("userId required");
        this.id = id;
        this.userId = userId;
    }

    /** FLOW: add item → merge by productId (domain-level, stock decrease happens in application using StockDomainService) */
    public void addOrIncrease(Long productId, int qty) {
        BasketItem existing = items.get(productId);
        if (existing == null) {
            items.put(productId, new BasketItem(null, productId, qty));
        } else {
            existing.addQuantity(qty);
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
