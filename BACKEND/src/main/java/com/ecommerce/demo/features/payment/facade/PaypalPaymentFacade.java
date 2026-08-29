package com.ecommerce.demo.features.payment.facade;

import com.ecommerce.demo.features.payment.dto.PaypalPaymentRequest;
import com.ecommerce.demo.features.payment.dto.PaypalPaymentResponse;
import com.ecommerce.demo.features.payment.service.PaypalPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaypalPaymentFacade {

    private final PaypalPaymentService paypalPaymentService;

    public PaypalPaymentResponse processPayment(PaypalPaymentRequest request) {
        return paypalPaymentService.processPayment(request);
    }
}
