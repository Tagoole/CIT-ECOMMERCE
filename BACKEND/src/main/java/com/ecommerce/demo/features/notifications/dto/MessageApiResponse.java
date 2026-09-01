package com.ecommerce.demo.features.notifications.dto;

public record MessageApiResponse<T>(
        String status,
        String message,
        T data,
        Object meta
) {
    public MessageApiResponse(String status, String message, T data){
        this(status,message,data,null);
    }
}
