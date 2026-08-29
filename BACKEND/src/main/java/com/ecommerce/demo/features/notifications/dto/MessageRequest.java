package com.ecommerce.demo.features.notifications.dto;

public record MessageRequest(
        Long senderId,
        Long productOwner,
) {
}
