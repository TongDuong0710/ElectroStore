package com.example.api.mapper;

import com.example.api.dto.ReceiptItemResponse;
import com.example.api.dto.ReceiptResponse;
import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.ReceiptDto;
import com.example.application.dto.ReceiptItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface ReceiptApiMapper {
    ReceiptItemResponse toResponse(ReceiptItemDto dto);
    ReceiptResponse toResponse(ReceiptDto dto);
}
