package com.ecommerce.demo.features.notifications.facade;

import com.ecommerce.demo.features.UserProfile.ApiResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserResponse;
import com.ecommerce.demo.features.UserProfile.service.UserService;
import com.ecommerce.demo.features.notifications.dto.NotificationRequest;
import com.ecommerce.demo.features.notifications.dto.NotificationResponse;
import com.ecommerce.demo.features.notifications.exception.NotFoundException;
import com.ecommerce.demo.features.notifications.mapper.NotificationMapper;
import com.ecommerce.demo.features.notifications.model.Notification;
import com.ecommerce.demo.features.notifications.service.NotificationMode;
import com.ecommerce.demo.features.notifications.service.NotificationRecordService;
import com.ecommerce.demo.features.notifications.service.NotificationService;
import com.ecommerce.demo.features.notifications.service.NotificationServiceFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NotificationFacade {
    private final NotificationServiceFactory notificationServiceFactory;
    private final NotificationRecordService notificationRecordService;
    private final NotificationMapper notificationMapper;
    private final UserService userService;

    public NotificationFacade(
            NotificationServiceFactory notificationServiceFactory,
            NotificationRecordService notificationRecordService,
            NotificationMapper notificationMapper,
            UserService userService
    ){
        this.notificationServiceFactory = notificationServiceFactory;
        this.notificationRecordService = notificationRecordService;
        this.notificationMapper = notificationMapper;
        this.userService = userService;
    }

    private UserResponse getVerifiedUser(Long userId){
        ApiResponse<UserResponse> response;
        try{
            response = userService.getUserById(userId);
        } catch (RuntimeException e) {
            throw new NotFoundException("No user found with id"+userId);
        }

        return response.getData();
    }

    private String resolveRecipient(UserResponse user, NotificationMode mode){
        return switch (mode){
            case EMAIL -> user.getEmail();
            case INAPP ,SMS-> user.getPhoneNumber();
        };
    }

    @Transactional
    public NotificationResponse sendNotification(NotificationRequest notificationRequest){
        UserResponse userResponse = getVerifiedUser(notificationRequest.recipientUserId());
        String recipient = resolveRecipient(userResponse,notificationRequest.mode());

        NotificationService channel = notificationServiceFactory.getService(notificationRequest.mode());
        channel.sendNotification(recipient,notificationRequest.title(), notificationRequest.body());

        Notification notificationToEntity= notificationMapper.toEntity(notificationRequest,recipient);
        Notification notificationToBeSaved = notificationRecordService.save(notificationToEntity);
        return notificationMapper.toResponse(notificationToBeSaved);
    }


    @Transactional(readOnly = true)
    public NotificationResponse findById(Long id){
        Notification notification = notificationRecordService.findById(id);
        return notificationMapper.toResponse(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> findAll(){
        return notificationRecordService.findAll().stream().map(
                notificationMapper::toResponse
        ).toList();
    }

    @Transactional
    public void deleteById(Long id)
    {
        notificationRecordService.deleteById(id);
    }
}
