package com.ecommerce.demo.features.notifications.mapper;


import com.ecommerce.demo.features.notifications.dto.NotificationRequest;
import com.ecommerce.demo.features.notifications.dto.NotificationResponse;
import com.ecommerce.demo.features.notifications.model.Notification;
import com.ecommerce.demo.features.notifications.service.NotificationMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationMapper {
    public Notification toEntity(NotificationRequest notificationRequest){
        Notification notification = new Notification();
        notification.setTitle(notificationRequest.title());
        notification.setBody(notificationRequest.body());
        notification.setRecipient(notificationRequest.recipient());

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
