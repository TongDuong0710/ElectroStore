package com.example.application.service;


import com.example.application.command.DealCreateCmd;
import com.example.application.dto.DealDto;
import com.example.application.dto.PageResult;

public interface AdminDealAppService {

    DealDto create(DealCreateCmd cmd);

    void delete(Long id);

    PageResult<DealDto> listDeals(int page, int size);
}
