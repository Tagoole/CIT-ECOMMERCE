package com.ecommerce.demo.features.notifications.mapper;


import com.ecommerce.demo.features.notifications.dto.MessageRequest;
import com.ecommerce.demo.features.notifications.dto.MessageResponse;
import com.ecommerce.demo.features.notifications.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toEntity(MessageRequest messageRequest){
        Message message = new Message();
        message.setUser(user);
        message.setProductOwner(productOwner);
        message.setText();
    }

    public MessageResponse toResponse(Message message){
        return new MessageResponse(
                me
        );
    }
}
