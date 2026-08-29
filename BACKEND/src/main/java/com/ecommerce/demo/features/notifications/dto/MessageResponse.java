package com.ecommerce.demo.features.notifications.dto;

public record MessageResponse(
        Long id,
        User user,
        User productOwner
) {
}
