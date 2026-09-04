package com.ecommerce.demo.features.payment.mapper;

import com.ecommerce.demo.features.payment.dto.AirtelPaymentRequest;
import com.ecommerce.demo.features.payment.dto.AirtelPaymentResponse;
import com.ecommerce.demo.features.payment.model.AirtelPaymentModel;
import org.springframework.stereotype.Component;

@Component
public class AirtelPaymentMapper {

    public AirtelPaymentModel toModel(AirtelPaymentRequest request) {
        AirtelPaymentModel model = new AirtelPaymentModel();
        model.setPhoneNumber(request.getPhoneNumber());
        model.setAmount(request.getAmount());
        model.setCurrency(request.getCurrency());
        return model;
    }

    public AirtelPaymentResponse toResponse(AirtelPaymentModel model) {
        AirtelPaymentResponse response = new AirtelPaymentResponse();
        response.setId(model.getId());
        response.setPhoneNumber(model.getPhoneNumber());
        response.setAmount(model.getAmount());
        response.setCurrency(model.getCurrency());
        response.setStatus(model.getStatus());
        response.setCreatedAt(model.getCreatedAt() != null ? model.getCreatedAt().toString() : null);
        return response;
    }
}
