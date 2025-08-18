package com.example.api.mapper;

import com.example.api.dto.ReceiptItemResponse;
import com.example.api.dto.ReceiptResponse;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.ReceiptDto;
import com.example.application.dto.ReceiptItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface ReceiptApiMapper {
    ReceiptItemResponse toResponse(ReceiptItemDto dto);

    @Mapping(target = "totalBeforeDiscount", source = "totalOriginal")
    @Mapping(target = "totalDiscount", source = "totalDiscount")
    ReceiptResponse toResponse(ReceiptDto dto);
}
