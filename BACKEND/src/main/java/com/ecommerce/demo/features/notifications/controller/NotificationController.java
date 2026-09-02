package com.ecommerce.demo.features.notifications.controller;

import com.ecommerce.demo.features.notifications.dto.*;
import com.ecommerce.demo.features.notifications.facade.MessageFacade;
import com.ecommerce.demo.features.notifications.facade.NotificationFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationFacade notificationFacade;

    public NotificationController(NotificationFacade notificationFacade){
        this.notificationFacade = notificationFacade;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@Valid @RequestBody NotificationRequest notificationRequest){
        NotificationResponse created = notificationFacade.sendNotification(notificationRequest);
        ApiResponse<NotificationResponse> body = new ApiResponse<>(
                "SUCCESS",
                "Notification Created successfully",
                created
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> findById(@PathVariable Long id){
        NotificationResponse message = notificationFacade.findById(id);
        ApiResponse<NotificationResponse> body = new ApiResponse<>(
                "SUCCESS",
                "Notification fetched Successfully",
                message
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> findAll(){
        List<NotificationResponse> notifications = notificationFacade.findAll();
        ApiResponse<List<NotificationResponse>> body = new ApiResponse<>(
                "SUCCESS",
                "Notifications fetched Successfully",
                notifications
        );

        return ResponseEntity.ok(body);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id){
        notificationFacade.deleteById(id);
        ApiResponse<Void> body = new ApiResponse<>(
                "SUCESS",
                "Notification Deleted Successfully",
                null
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);

    }

}
