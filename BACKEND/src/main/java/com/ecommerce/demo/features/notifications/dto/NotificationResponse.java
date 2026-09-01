package com.ecommerce.demo.features.notifications.dto;

import com.ecommerce.demo.features.notifications.service.NotificationMode;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        NotificationMode mode,
        String title,
        String body,
        String recipient,
        LocalDateTime createdAt
) {
}
