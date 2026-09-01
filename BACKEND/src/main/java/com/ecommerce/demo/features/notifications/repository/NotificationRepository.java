package com.ecommerce.demo.features.notifications.repository;

import com.ecommerce.demo.features.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
