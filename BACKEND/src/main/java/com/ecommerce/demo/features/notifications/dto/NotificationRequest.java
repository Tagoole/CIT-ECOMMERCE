package com.ecommerce.demo.features.notifications.dto;


public record NotificationRequest(
        String mode,
        String title,
        String body,
        String recipient
) {
}
