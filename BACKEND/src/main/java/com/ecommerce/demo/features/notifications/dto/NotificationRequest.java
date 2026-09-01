package com.ecommerce.demo.features.notifications.dto;


import com.ecommerce.demo.features.notifications.service.NotificationMode;

public record NotificationRequest(
        Long recipientUserId,
        NotificationMode mode,
        String title,
        String body

) {
}
