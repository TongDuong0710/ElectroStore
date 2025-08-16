package com.example.application.command;

import java.time.LocalDateTime;

public record DealCreateCmd(
        Long productId,
        String description,
        LocalDateTime expirationDateTime
) {}

