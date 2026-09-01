package com.ecommerce.demo.features.notifications.service;


import com.ecommerce.demo.features.notifications.exception.MessageNotFoundException;
import com.ecommerce.demo.features.notifications.model.Message;
import com.ecommerce.demo.features.notifications.model.Notification;
import com.ecommerce.demo.features.notifications.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message save(Message message){
        return messageRepository.save(message);
    }

    public Message findById(Long id){
        return messageRepository.findById(id).orElseThrow(
                ()-> new MessageNotFoundException("Message with id not found"+id)
        );
    }

    public List<Message> findAll(){
        return messageRepository.findAll();
    }

    public void deleteById(Long id){
        if (!messageRepository.existsById(id)){
            throw new MessageNotFoundException("Message Not found with id"+id);
        }
        messageRepository.deleteById(id);
    }
}
