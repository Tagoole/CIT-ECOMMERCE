package com.ecommerce.demo.features.payment.facade;

import com.ecommerce.demo.features.payment.dto.CreditCardPaymentRequest;
import com.ecommerce.demo.features.payment.dto.CreditCardPaymentResponse;
import com.ecommerce.demo.features.payment.service.CreditCardPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCardPaymentFacade {

    private final CreditCardPaymentService creditCardPaymentService;

    public CreditCardPaymentResponse processPayment(CreditCardPaymentRequest request) {
        return creditCardPaymentService.processPayment(request);
    }
}
