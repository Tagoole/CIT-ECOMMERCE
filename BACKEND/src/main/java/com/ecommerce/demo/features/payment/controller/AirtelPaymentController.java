package com.ecommerce.demo.features.payment.controller;

import com.ecommerce.demo.features.payment.dto.AirtelPaymentRequest;
import com.ecommerce.demo.features.payment.dto.AirtelPaymentResponse;
import com.ecommerce.demo.features.payment.facade.AirtelPaymentFacade;
import com.ecommerce.demo.features.payment.service.AirtelPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment/airtel")
@RequiredArgsConstructor
public class AirtelPaymentController {

    private final AirtelPaymentFacade airtelPaymentFacade;
    private final AirtelPaymentService airtelPaymentService;

    @PostMapping("/process")
    public ResponseEntity<AirtelPaymentResponse> processPayment(@RequestBody AirtelPaymentRequest request) {
        return ResponseEntity.ok(airtelPaymentFacade.processPayment(request));
    }

    @GetMapping
    public ResponseEntity<List<AirtelPaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(airtelPaymentService.getAllPayments());
    }
}
