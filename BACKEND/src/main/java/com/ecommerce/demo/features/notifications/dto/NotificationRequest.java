package com.ecommerce.demo.features.notifications.dto;


import com.ecommerce.demo.features.notifications.service.NotificationMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationRequest(
        @NotNull(message = "Recipient user ID is required")
        Long recipientUserId,

        @NotNull(message = "Notification mode is required")
        NotificationMode mode,

        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Body is required")
        @Size(max = 2000, message = "Body must not exceed 2000 characters")
        String body
) {
}
