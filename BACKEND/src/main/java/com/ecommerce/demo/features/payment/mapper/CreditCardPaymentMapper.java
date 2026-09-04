package com.ecommerce.demo.features.payment.mapper;

import com.ecommerce.demo.features.payment.dto.CreditCardPaymentRequest;
import com.ecommerce.demo.features.payment.dto.CreditCardPaymentResponse;
import com.ecommerce.demo.features.payment.model.CreditCardPaymentModel;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentMapper {

    public CreditCardPaymentModel toModel(CreditCardPaymentRequest request) {
        CreditCardPaymentModel model = new CreditCardPaymentModel();
        model.setCardNumber(request.getCardNumber());
        model.setCardHolder(request.getCardHolder());
        model.setAmount(request.getAmount());
        model.setCurrency(request.getCurrency());
        return model;
    }

    public CreditCardPaymentResponse toResponse(CreditCardPaymentModel model) {
        CreditCardPaymentResponse response = new CreditCardPaymentResponse();
        response.setId(model.getId());
        response.setCardNumber(model.getCardNumber());
        response.setCardHolder(model.getCardHolder());
        response.setAmount(model.getAmount());
        response.setCurrency(model.getCurrency());
        response.setStatus(model.getStatus());
        response.setCreatedAt(model.getCreatedAt() != null ? model.getCreatedAt().toString() : null);
        return response;
    }
}
