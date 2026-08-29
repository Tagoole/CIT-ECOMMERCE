package com.ecommerce.demo.features.payment.controller;

import com.ecommerce.demo.features.payment.dto.PaypalPaymentRequest;
import com.ecommerce.demo.features.payment.dto.PaypalPaymentResponse;
import com.ecommerce.demo.features.payment.facade.PaypalPaymentFacade;
import com.ecommerce.demo.features.payment.service.PaypalPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment/paypal")
@RequiredArgsConstructor
public class PaypalPaymentController {

    private final PaypalPaymentFacade paypalPaymentFacade;
    private final PaypalPaymentService paypalPaymentService;

    @PostMapping("/process")
    public ResponseEntity<PaypalPaymentResponse> processPayment(@RequestBody PaypalPaymentRequest request) {
        return ResponseEntity.ok(paypalPaymentFacade.processPayment(request));
    }

    @GetMapping
    public ResponseEntity<List<PaypalPaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paypalPaymentService.getAllPayments());
    }
}
