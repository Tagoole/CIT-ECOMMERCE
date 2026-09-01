package com.ecommerce.demo.features.notifications.dto;


import java.time.LocalDateTime;


public record MessageResponse(
        Long id,
        Long senderId,
        Long receiverId,
        String text,
        LocalDateTime createdAt
) {
}
