package com.ecommerce.demo.features.notifications.repository;

import com.ecommerce.demo.features.notifications.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{
}
