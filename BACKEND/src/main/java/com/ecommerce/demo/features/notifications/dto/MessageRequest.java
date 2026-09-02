package com.ecommerce.demo.features.notifications.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageRequest(
        @NotNull(message = "Sender ID is required")
        Long senderId,

        @NotNull(message = "Receiver ID is required")
        Long receiverId,

        @NotBlank(message = "Message text is required")
        @Size(max = 2000, message = "Message text must not exceed 2000 characters")
        String text
) {
}
