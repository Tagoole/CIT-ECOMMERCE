package com.ecommerce.demo.features.notifications.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(String message){
        super(message);
    }
}
