package com.ecommerce.demo.features.payment.service;

import com.ecommerce.demo.features.payment.dto.CreditCardPaymentRequest;
import com.ecommerce.demo.features.payment.dto.CreditCardPaymentResponse;
import com.ecommerce.demo.features.payment.exception.CreditCardPaymentException;
import com.ecommerce.demo.features.payment.mapper.CreditCardPaymentMapper;
import com.ecommerce.demo.features.payment.model.CreditCardPaymentModel;
import com.ecommerce.demo.features.payment.repository.CreditCardPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentService {

    private final CreditCardPaymentRepository creditCardPaymentRepository;
    private final CreditCardPaymentMapper creditCardPaymentMapper;

    public CreditCardPaymentResponse processPayment(CreditCardPaymentRequest request) {
        try {
            CreditCardPaymentModel model = creditCardPaymentMapper.toModel(request);
            model.setStatus("PENDING");
            CreditCardPaymentModel saved = creditCardPaymentRepository.save(model);
            return creditCardPaymentMapper.toResponse(saved);
        } catch (Exception e) {
            throw new CreditCardPaymentException("Failed to process Credit Card payment: " + e.getMessage());
        }
    }

    public List<CreditCardPaymentResponse> getAllPayments() {
        return creditCardPaymentRepository.findAll()
                .stream()
                .map(creditCardPaymentMapper::toResponse)
                .toList();
    }
}
