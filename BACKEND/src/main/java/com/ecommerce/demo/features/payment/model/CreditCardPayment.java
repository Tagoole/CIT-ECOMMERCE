package com.ecommerce.demo.features.payment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "credit_card_payments")
public class CreditCardPayment extends PaymentMethod {

    @Column(nullable = false)
    private String maskedCardNumber;

    @Column(nullable = false)
    private String cardHolder;
}
