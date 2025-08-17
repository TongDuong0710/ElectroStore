package com.example.application.mapper;

import com.example.application.configurations.MapStructCentralConfig;
import com.example.application.dto.ReceiptDto;
import com.example.application.dto.ReceiptItemDto;
import com.example.domain.model.Receipt;
import com.example.domain.model.ReceiptItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructCentralConfig.class)
public interface ReceiptAppMapper {

    ReceiptDto toDto(Receipt receipt);

    ReceiptItemDto toItemDto(ReceiptItem item);
}
