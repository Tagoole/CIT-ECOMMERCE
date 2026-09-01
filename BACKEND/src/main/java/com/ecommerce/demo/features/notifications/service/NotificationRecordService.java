package com.ecommerce.demo.features.notifications.service;

import com.ecommerce.demo.features.notifications.exception.NotificationNotFoundException;

import com.ecommerce.demo.features.notifications.model.Notification;
import com.ecommerce.demo.features.notifications.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationRecordService {
    private final NotificationRepository notificationRepository;

    public NotificationRecordService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
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
