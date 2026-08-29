package com.ecommerce.demo.features.notifications.mapper;


import com.ecommerce.demo.features.notifications.dto.NotificationRequest;
import com.ecommerce.demo.features.notifications.dto.NotificationResponse;
import com.ecommerce.demo.features.notifications.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toEntity(NotificationRequest notificationRequest){
        Notification notification = new Notification();
        notification.setBody(notificationRequest.body());
        notification.setTitle(notificationRequest.title());

        return notification;
    }


    public NotificationResponse toResponse(Notification notification){
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getBody()
        );
    }
}
