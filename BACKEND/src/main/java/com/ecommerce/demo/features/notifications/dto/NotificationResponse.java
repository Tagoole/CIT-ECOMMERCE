package com.ecommerce.demo.features.notifications.dto;

public record NotificationResponse(
        Long id,
        String title,
        String body
) {
}
