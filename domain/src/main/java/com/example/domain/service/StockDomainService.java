package com.example.domain.service;

import com.example.domain.model.Product;

/** Domain-level stock operations (pure rules; transactions handled at application layer) */
public class StockDomainService {

    /** FLOW: when adding to basket → decrement stock atomically around repository calls at application layer */
    public void holdStock(Product product, int qty) {
        product.decrementStock(qty);
    }

    /** FLOW: when removing from basket → restore stock */
    public void releaseStock(Product product, int qty) {
        product.incrementStock(qty);
    }
}
