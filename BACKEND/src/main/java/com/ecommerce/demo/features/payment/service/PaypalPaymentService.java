package com.ecommerce.demo.features.payment.service;

import com.ecommerce.demo.features.payment.dto.PaypalPaymentRequest;
import com.ecommerce.demo.features.payment.dto.PaypalPaymentResponse;
import com.ecommerce.demo.features.payment.exception.PaypalPaymentException;
import com.ecommerce.demo.features.payment.mapper.PaypalPaymentMapper;
import com.ecommerce.demo.features.payment.model.PaypalPaymentModel;
import com.ecommerce.demo.features.payment.repository.PaypalPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaypalPaymentService {

    private final PaypalPaymentRepository paypalPaymentRepository;
    private final PaypalPaymentMapper paypalPaymentMapper;

    public PaypalPaymentResponse processPayment(PaypalPaymentRequest request) {
        try {
            PaypalPaymentModel model = paypalPaymentMapper.toModel(request);
            model.setStatus("PENDING");
            PaypalPaymentModel saved = paypalPaymentRepository.save(model);
            return paypalPaymentMapper.toResponse(saved);
        } catch (Exception e) {
            throw new PaypalPaymentException("Failed to process PayPal payment: " + e.getMessage());
        }
    }

    public List<PaypalPaymentResponse> getAllPayments() {
        return paypalPaymentRepository.findAll()
                .stream()
                .map(paypalPaymentMapper::toResponse)
                .toList();
    }
}
