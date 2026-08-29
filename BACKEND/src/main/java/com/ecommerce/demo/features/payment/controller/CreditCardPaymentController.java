package com.ecommerce.demo.features.payment.controller;

import com.ecommerce.demo.features.payment.dto.CreditCardPaymentRequest;
import com.ecommerce.demo.features.payment.dto.CreditCardPaymentResponse;
import com.ecommerce.demo.features.payment.facade.CreditCardPaymentFacade;
import com.ecommerce.demo.features.payment.service.CreditCardPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment/credit-card")
@RequiredArgsConstructor
public class CreditCardPaymentController {

    private final CreditCardPaymentFacade creditCardPaymentFacade;
    private final CreditCardPaymentService creditCardPaymentService;

    @PostMapping("/process")
    public ResponseEntity<CreditCardPaymentResponse> processPayment(@RequestBody CreditCardPaymentRequest request) {
        return ResponseEntity.ok(creditCardPaymentFacade.processPayment(request));
    }

    @GetMapping
    public ResponseEntity<List<CreditCardPaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(creditCardPaymentService.getAllPayments());
    }
}
