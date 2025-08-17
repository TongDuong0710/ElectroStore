//package com.example.infra.adapter;
//
//import com.example.application.provider.BasketItemRepository;
//import com.example.domain.model.BasketItem;
//import com.example.infra.entity.BasketItemEntity;
//import com.example.infra.mapper.BasketItemMapper;
//import com.example.infra.repository.BasketItemJpaRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//@RequiredArgsConstructor
//public class BasketItemRepositoryAdapter implements BasketItemRepository {
//
//    private final BasketItemJpaRepository basketItemJpaRepository;
//    private final BasketItemMapper basketItemMapper;
//
//    @Override
//    public Optional<BasketItem> findByBasketAndProduct(Long productId, Long basketId) {
//        return basketItemJpaRepository.findByBasketIdAndProductId(basketId, productId)
//                .map(basketItemMapper::toDomain);
//    }
//
//    @Override
//    public BasketItem save(BasketItem item) {
//        // Uncomment the following lines if you want to validate the existence of Basket and Product
//        // before saving the BasketItem. This is optional and depends on your business logic.
//        // Ensure that the basket and product exist before saving the item
////        BasketEntity basket = basketJpaRepository.findById(item.getBasketId())
////                .orElseThrow(() -> new IllegalArgumentException("Basket not found: " + item.getBasketId()));
////        ProductEntity product = productJpaRepository.findById(item.getProductId())
////                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));
//
//        BasketItemEntity entity = basketItemMapper.toNewEntity(item.getBasketId(), item);
//        BasketItemEntity saved = basketItemJpaRepository.save(entity);
//        return basketItemMapper.toDomain(saved);
//    }
//
//    @Override
//    public void deleteByBasketIdAndProductId(Long basketId, Long productId) {
//        basketItemJpaRepository.deleteByBasketIdAndProductId(basketId, productId);
//    }
//}
