package com.example.infra.adapter;

import com.example.application.provider.BasketRepository;
import com.example.domain.model.Basket;
import com.example.infra.entity.BasketEntity;
import com.example.infra.entity.BasketItemEntity;
import com.example.infra.mapper.BasketItemMapper;
import com.example.infra.mapper.BasketMapper;
import com.example.infra.repository.BasketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryAdapter implements BasketRepository {
    private final BasketJpaRepository jpa;
    private final BasketMapper basketMapper;
    private final BasketItemMapper basketItemMapper;
    private final jakarta.persistence.EntityManager em;

    @Override
    public Optional<Basket> findOpenByUserId(String userId) {
        return jpa.findByUserId(userId).map(basketMapper::toDomain);
    }

    @Override
    @Transactional
    public Basket save(Basket basket) {
        BasketEntity e;

        if (basket.getId() == null) {
            // INSERT: tạo parent trước để có id
            e = new BasketEntity();
            e.setUserId(basket.getUserId());
            e.setItems(new ArrayList<>());
            e = jpa.save(e); // e.getId() đã có
        } else {
            // UPDATE: load managed entity (giữ version/quan hệ)
            e = jpa.findById(basket.getId())
                    .orElseThrow(() -> new IllegalStateException("Basket not found: " + basket.getId()));
            e.setUserId(basket.getUserId());
            e.getItems().clear(); // replace toàn bộ theo state domain
        }

        Long realBasketId = e.getId();

        // map items với basketId THỰC TẾ + product as managed reference
        List<BasketItemEntity> itemEntities = basket.getItems().stream()
                .map(it -> basketItemMapper.toNewEntity(realBasketId, em, it))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        // nếu BasketEntity có @OneToMany(cascade = ALL, orphanRemoval = true)
        e.getItems().addAll(itemEntities);
        BasketEntity saved = jpa.save(e);

        return basketMapper.toDomain(saved);
    }
}
