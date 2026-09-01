package com.ecommerce.demo.features.notifications.mapper;


import com.ecommerce.demo.features.notifications.dto.NotificationRequest;
import com.ecommerce.demo.features.notifications.dto.NotificationResponse;
import com.ecommerce.demo.features.notifications.model.Notification;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;


@Component
public class NotificationMapper {
    public Notification toEntity(NotificationRequest notificationRequest, String recipient ){
        Notification notification = new Notification();
        notification.setMode(notificationRequest.mode());
        notification.setTitle(notificationRequest.title());
        notification.setBody(notificationRequest.body());
        notification.setRecipient(recipient);


        return notification;
    }

    public NotificationResponse toResponse(Notification notification){
        return new NotificationResponse(
                notification.getId(),
                notification.getMode(),
                notification.getTitle(),
                notification.getBody(),
                notification.getRecipient(),
                notification.getCreatedAt()

        );
    }
}
