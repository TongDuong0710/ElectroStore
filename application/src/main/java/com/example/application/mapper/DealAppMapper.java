package com.example.application.mapper;

import com.example.application.command.DealCreateCmd;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.DealDto;
import com.example.domain.model.Deal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface DealAppMapper {

    Deal toDomain(DealCreateCmd cmd);

    DealDto toDto(Deal deal);
}

