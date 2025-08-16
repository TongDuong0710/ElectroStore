package com.example.application.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseCode {
    SUCCESS(OK, "ES-0000", "Success"),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "ES-5000", "Server error"),
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