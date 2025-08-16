package com.example.application.mapper;

import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.PageResult;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(config = MapStructCentralConfig.class)
public interface PageAppMapper {

    default <T, R> PageResult<R> toPageResult(Page<T> page, List<R> content) {
        return new PageResult<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}
