package com.ecommerce.demo.features.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MTNPaymentException extends RuntimeException {

    public MTNPaymentException(String message) {
        super(message);
    }
}
