package com.example.application.service.impl;


import com.example.application.dto.DealCreateDto;
import com.example.application.dto.DealDto;
import com.example.application.dto.PageResult;
import com.example.application.mapper.DealAppMapper;
import com.example.application.provider.DealRepository;
import com.example.application.service.AdminDealAppService;
import com.example.domain.model.Deal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDealAppServiceImpl implements AdminDealAppService {

    private final DealRepository dealRepository;
    private final DealAppMapper dealMapper;

    @Override
    public DealDto create(DealCreateDto cmd) {
        Deal deal = dealMapper.toDomain(cmd);
        deal = dealRepository.save(deal);
        return dealMapper.toDto(deal);
    }

    @Override
    public void delete(Long id) {
        dealRepository.deleteById(id);

    }

    @Override
    public PageResult<DealDto> listDeals(int page, int size) {
        List<Deal> deals = dealRepository.findAll(page, size);
        long total = dealRepository.count();
        int totalPages = (int) Math.ceil((double) total / size);
        return new PageResult<>(
                deals.stream().map(dealMapper::toDto).toList(),
                total, totalPages, page, size
        );
    }
}
