package com.ecommerce.demo.features.payment.facade;

import com.ecommerce.demo.features.payment.dto.MtnPaymentRequest;
import com.ecommerce.demo.features.payment.dto.MtnPaymentResponse;
import com.ecommerce.demo.features.payment.service.MTNPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MTNPaymentFacade {

    private final MTNPaymentService mtnPaymentService;

    public MtnPaymentResponse processPayment(MtnPaymentRequest request) {
        return mtnPaymentService.processPayment(request);
    }
}
