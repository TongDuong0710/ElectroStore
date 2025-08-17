package com.example.api.mapper;

import com.example.api.dto.DealCreateRequest;
import com.example.api.dto.DealResponse;
import com.example.api.dto.base.PageResponse;
import com.example.application.command.DealCreateCmd;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.DealDto;
import com.example.application.dto.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapStructCentralConfig.class)
public interface DealApiMapper {

    @Mapping(target = "expirationDateTime", source = "expirationDateTime")
    DealCreateCmd toCommand(DealCreateRequest request);

    DealResponse toResponse(DealDto dto);

    default PageResponse<DealResponse> toPageResponse(PageResult<DealDto> page) {
        List<DealResponse> content = page.content().stream()
                .map(this::toResponse)
                .toList();

        return new PageResponse<>(
                content,
                page.page(),
                page.size(),
                page.totalElements()
        );
    }
}
