package com.ecommerce.demo.features.notifications.exception;

public class NotificationServiceNotFoundException extends RuntimeException {
    public NotificationServiceNotFoundException(String message) {
        super(message);
    }
}
