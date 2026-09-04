package com.ecommerce.demo.features.payment.facade;

import com.ecommerce.demo.features.payment.dto.AirtelPaymentRequest;
import com.ecommerce.demo.features.payment.dto.AirtelPaymentResponse;
import com.ecommerce.demo.features.payment.service.AirtelPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirtelPaymentFacade {

    private final AirtelPaymentService airtelPaymentService;

    public AirtelPaymentResponse processPayment(AirtelPaymentRequest request) {
        return airtelPaymentService.processPayment(request);
    }
}
