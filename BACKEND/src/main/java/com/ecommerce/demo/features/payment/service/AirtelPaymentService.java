package com.ecommerce.demo.features.payment.service;

import com.ecommerce.demo.features.payment.dto.AirtelPaymentRequest;
import com.ecommerce.demo.features.payment.dto.AirtelPaymentResponse;
import com.ecommerce.demo.features.payment.exception.AirtelPaymentException;
import com.ecommerce.demo.features.payment.mapper.AirtelPaymentMapper;
import com.ecommerce.demo.features.payment.model.AirtelPaymentModel;
import com.ecommerce.demo.features.payment.repository.AirtelPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirtelPaymentService {

    private final AirtelPaymentRepository airtelPaymentRepository;
    private final AirtelPaymentMapper airtelPaymentMapper;

    public AirtelPaymentResponse processPayment(AirtelPaymentRequest request) {
        try {
            AirtelPaymentModel model = airtelPaymentMapper.toModel(request);
            model.setStatus("PENDING");
            AirtelPaymentModel saved = airtelPaymentRepository.save(model);
            return airtelPaymentMapper.toResponse(saved);
        } catch (Exception e) {
            throw new AirtelPaymentException("Failed to process Airtel payment: " + e.getMessage());
        }
    }

    public List<AirtelPaymentResponse> getAllPayments() {
        return airtelPaymentRepository.findAll()
                .stream()
                .map(airtelPaymentMapper::toResponse)
                .toList();
    }
}
