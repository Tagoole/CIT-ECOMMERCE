package com.ecommerce.demo.features.payment.service;

import com.ecommerce.demo.features.payment.dto.MtnPaymentRequest;
import com.ecommerce.demo.features.payment.dto.MtnPaymentResponse;
import com.ecommerce.demo.features.payment.exception.MTNPaymentException;
import com.ecommerce.demo.features.payment.mapper.MTNPaymentMapper;
import com.ecommerce.demo.features.payment.model.MTNPaymentModel;
import com.ecommerce.demo.features.payment.repository.MTNPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MTNPaymentService {

    private final MTNPaymentRepository mtnPaymentRepository;
    private final MTNPaymentMapper mtnPaymentMapper;

    public MtnPaymentResponse processPayment(MtnPaymentRequest request) {
        try {
            MTNPaymentModel model = mtnPaymentMapper.toModel(request);
            model.setStatus("PENDING");
            MTNPaymentModel saved = mtnPaymentRepository.save(model);
            return mtnPaymentMapper.toResponse(saved);
        } catch (Exception e) {
            throw new MTNPaymentException("Failed to process MTN payment: " + e.getMessage());
        }
    }

    public List<MtnPaymentResponse> getAllPayments() {
        return mtnPaymentRepository.findAll()
                .stream()
                .map(mtnPaymentMapper::toResponse)
                .toList();
    }
}
