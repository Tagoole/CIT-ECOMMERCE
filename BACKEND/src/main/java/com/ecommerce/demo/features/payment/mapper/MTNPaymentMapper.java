package com.ecommerce.demo.features.payment.mapper;

import com.ecommerce.demo.features.payment.dto.MtnPaymentRequest;
import com.ecommerce.demo.features.payment.dto.MtnPaymentResponse;
import com.ecommerce.demo.features.payment.model.MTNPaymentModel;
import org.springframework.stereotype.Component;

@Component
public class MTNPaymentMapper {

    public MTNPaymentModel toModel(MtnPaymentRequest request) {
        MTNPaymentModel model = new MTNPaymentModel();
        model.setPhoneNumber(request.getPhoneNumber());
        model.setAmount(request.getAmount());
        model.setCurrency(request.getCurrency());
        return model;
    }

    public MtnPaymentResponse toResponse(MTNPaymentModel model) {
        MtnPaymentResponse response = new MtnPaymentResponse();
        response.setId(model.getId());
        response.setPhoneNumber(model.getPhoneNumber());
        response.setAmount(model.getAmount());
        response.setCurrency(model.getCurrency());
        response.setStatus(model.getStatus());
        response.setCreatedAt(model.getCreatedAt() != null ? model.getCreatedAt().toString() : null);
        return response;
    }
}
