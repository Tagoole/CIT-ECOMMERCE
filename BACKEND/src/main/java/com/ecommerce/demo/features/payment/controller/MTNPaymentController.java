package com.ecommerce.demo.features.payment.controller;

import com.ecommerce.demo.features.payment.dto.MtnPaymentRequest;
import com.ecommerce.demo.features.payment.dto.MtnPaymentResponse;
import com.ecommerce.demo.features.payment.facade.MTNPaymentFacade;
import com.ecommerce.demo.features.payment.service.MTNPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment/mtn")
@RequiredArgsConstructor
public class MTNPaymentController {

    private final MTNPaymentFacade mtnPaymentFacade;
    private final MTNPaymentService mtnPaymentService;

    @PostMapping("/process")
    public ResponseEntity<MtnPaymentResponse> processPayment(@RequestBody MtnPaymentRequest request) {
        return ResponseEntity.ok(mtnPaymentFacade.processPayment(request));
    }

    @GetMapping
    public ResponseEntity<List<MtnPaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(mtnPaymentService.getAllPayments());
    }
}
