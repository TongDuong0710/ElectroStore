package com.example.domain.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseCode{
    NOT_FOUND(BAD_REQUEST, "DM-4000", "Server error"),
    INSUFFICIENT_STOCK(CONFLICT, "DM-4001", "Insufficient stock"),

    REQUIRE_USER_ID(BAD_REQUEST, "DM-4002", "UserId required"),
    PRODUCT_NOT_FOUND(BAD_REQUEST, "DM-4003", "Product not found"),
    INVALID_PARAM(BAD_REQUEST, "DM-4004", "Invalid parameter"),

    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    private static final Map<String, ResponseCode> CODE_MAP = new HashMap<>();

    static {
        for (ResponseCode responseCode : values()) {
            CODE_MAP.put(responseCode.code, responseCode);
        }
    }
}