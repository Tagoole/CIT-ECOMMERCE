package com.ecommerce.demo.features.notifications.mapper;


import com.ecommerce.demo.features.notifications.dto.MessageRequest;
import com.ecommerce.demo.features.notifications.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toEntity(User user, MessageRequest messageRequest){
        Message message = new Message();
        message.setUser(user);
        message.setProductOwner();
        message.setText();
    }
}
