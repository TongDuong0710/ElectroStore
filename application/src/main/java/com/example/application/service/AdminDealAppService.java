package com.example.application.service;


import com.example.application.dto.DealCreateDto;
import com.example.application.dto.DealDto;
import com.example.application.dto.PageResult;

public interface AdminDealAppService {

    DealDto create(DealCreateDto cmd);

    void delete(Long id);

    PageResult<DealDto> listDeals(int page, int size);
}
