package com.example.application.service;


import com.example.application.dto.ReceiptDto;

public interface ReceiptAppService {

    ReceiptDto generateReceipt(String userId);
}
