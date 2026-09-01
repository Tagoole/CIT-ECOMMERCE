package com.ecommerce.demo.features.notifications.service;

public interface NotificationService {
    public NotificationMode mode();
    public void sendNotification(String recipient, String title, String body);
}
