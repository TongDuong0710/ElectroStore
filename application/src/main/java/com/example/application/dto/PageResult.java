package com.example.application.dto;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int page,
        int size
) {}
