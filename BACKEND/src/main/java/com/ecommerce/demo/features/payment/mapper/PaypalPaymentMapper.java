package com.ecommerce.demo.features.payment.mapper;

import com.ecommerce.demo.features.payment.dto.PaypalPaymentRequest;
import com.ecommerce.demo.features.payment.dto.PaypalPaymentResponse;
import com.ecommerce.demo.features.payment.model.PaypalPaymentModel;
import org.springframework.stereotype.Component;

@Component
public class PaypalPaymentMapper {

    public PaypalPaymentModel toModel(PaypalPaymentRequest request) {
        PaypalPaymentModel model = new PaypalPaymentModel();
        model.setEmail(request.getEmail());
        model.setAmount(request.getAmount());
        model.setCurrency(request.getCurrency());
        return model;
    }

    public PaypalPaymentResponse toResponse(PaypalPaymentModel model) {
        PaypalPaymentResponse response = new PaypalPaymentResponse();
        response.setId(model.getId());
        response.setEmail(model.getEmail());
        response.setAmount(model.getAmount());
        response.setCurrency(model.getCurrency());
        response.setStatus(model.getStatus());
        response.setCreatedAt(model.getCreatedAt() != null ? model.getCreatedAt().toString() : null);
        return response;
    }
}
