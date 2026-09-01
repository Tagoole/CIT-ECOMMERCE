package com.ecommerce.demo.features.notifications.mapper;


import com.ecommerce.demo.features.UserProfile.model.UserModel;
import com.ecommerce.demo.features.notifications.dto.MessageRequest;
import com.ecommerce.demo.features.notifications.dto.MessageResponse;
import com.ecommerce.demo.features.notifications.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toEntity(MessageRequest messageRequest){
        Message message = new Message();
        message.setSenderId(messageRequest.senderId());
        message.setReceiverId(messageRequest.receiverId());
        message.setText(messageRequest.text());

        return  message;
    }

    public MessageResponse toResponse(Message message){
        return new MessageResponse(
                message.getId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getText(),
                message.getCreatedAt()

        );
    }
}
