package com.ecommerce.demo.features.notifications.service;


import com.ecommerce.demo.features.notifications.exception.NotificationNotFoundException;
import com.ecommerce.demo.features.notifications.exception.NotificationServiceNotFoundException;
import com.ecommerce.demo.features.notifications.model.Notification;
import com.ecommerce.demo.features.notifications.repository.NotificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
class EmailNotificationService implements NotificationService{

    @Override
    public NotificationMode mode() {
        return NotificationMode.EMAIL;
    }

    @Override
    public void sendNotification(String recipient, String title, String body) {
        System.out.println("Sent Email Notification to: "+recipient+" of title: "+title+" and of body: "+body);
    }
}






@Component
class SMSNotificationService implements NotificationService{

    @Override
    public NotificationMode mode() {
        return NotificationMode.SMS;
    }

    @Override
    public void sendNotification(String recipient, String title, String body) {
        System.out.println("Sent SMS Notification to: "+recipient+" of title: "+title+" and of body: "+body);
    }
}




@Component
class INAPPNotificationService implements NotificationService{

    @Override
    public NotificationMode mode() {
        return NotificationMode.INAPP;
    }

    @Override
    public void sendNotification(String recipient, String title, String body) {
        System.out.println("Sent In App Notification to: "+recipient+" of title: "+title+" and of body: "+body);
    }
}




@Service
public class NotificationServiceFactory {
    Map<NotificationMode,NotificationService> services;
    private final NotificationRepository notificationRepository;

    public NotificationServiceFactory(List<NotificationService> allServices, NotificationRepository notificationRepository){
        this.services = allServices.stream()
                .collect(Collectors.toMap(NotificationService::mode,s->s));
        this.notificationRepository = notificationRepository;
    }

    public NotificationService getService(NotificationMode notificationMode){
        NotificationService selectedService = services.get(notificationMode);
        if (selectedService == null){
            throw new NotificationServiceNotFoundException("No notification service found for mode:"+notificationMode);
        }
        return  selectedService;
    }

    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }

    public Notification findById(Long id){
        return notificationRepository.findById(id).orElseThrow(
                ()-> new NotificationNotFoundException("Notification not found with id "+id)
        );
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    public void deleteById(Long id){
        if(!notificationRepository.existsById(id)){
            throw new NotificationNotFoundException("Notification not found with id"+id);
        }
        notificationRepository.deleteById(id);
    }



}
