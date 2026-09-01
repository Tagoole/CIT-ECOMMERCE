package com.ecommerce.demo.features.notifications.dto;

public record NotificationApiResponse<T>(
        String status,
        String message,
        T data,
        Object meta
) {
    public NotificationApiResponse(String status, String message, T data){
        this(status,message,data,null);
    }
}
