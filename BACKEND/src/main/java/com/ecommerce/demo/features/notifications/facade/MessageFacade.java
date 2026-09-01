package com.ecommerce.demo.features.notifications.facade;


import com.ecommerce.demo.features.UserProfile.service.UserService;
import com.ecommerce.demo.features.notifications.dto.MessageRequest;
import com.ecommerce.demo.features.notifications.dto.MessageResponse;
import com.ecommerce.demo.features.notifications.exception.NotFoundException;
import com.ecommerce.demo.features.notifications.mapper.MessageMapper;
import com.ecommerce.demo.features.notifications.model.Message;
import com.ecommerce.demo.features.notifications.service.MessageService;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageFacade {
    private final MessageMapper messageMapper;
    private final MessageService messageService;
    private final UserService userService;

    public MessageFacade(MessageMapper messageMapper, MessageService messageService,UserService userService){
        this.messageMapper = messageMapper;
        this.messageService = messageService;
        this.userService = userService;
    }

    private void verifyUserExists(Long userId){
        try{
            userService.getUserById(userId);
        } catch (RuntimeException exception){
            throw new NotFoundException("No User found with id"+userId);
        }
    }

    @Transactional
    public MessageResponse create(MessageRequest messageRequest){
        //Long senderId = userService.getUserById(messageRequest.senderId());
        verifyUserExists(messageRequest.senderId());
        verifyUserExists(messageRequest.receiverId());
        //Long receiverId,
        Message message = messageMapper.toEntity(messageRequest);
        Message savedMessage = messageService.save(message);
        return messageMapper.toResponse(savedMessage);
    }

    @Transactional(readOnly = true)
    public MessageResponse findById(Long id){
        Message message = messageService.findById(id);
        return messageMapper.toResponse(message);
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> findAll(){
        return messageService.findAll().stream().map(messageMapper::toResponse).toList();
    }


    @Transactional
    public MessageResponse update(Long id, MessageRequest message){
        Message existingMessage = messageService.findById(id);
        existingMessage.setSenderId(message.senderId());
        existingMessage.setReceiverId(message.receiverId());
        existingMessage.setText(message.text());

        Message updatedMessage=messageService.save(existingMessage);

        return messageMapper.toResponse(updatedMessage);
    }


    @Transactional
    public void deleteById(Long id){
        messageService.deleteById(id);
    }
}
