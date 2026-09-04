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
@Table(name = "airtel_payments")
public class AirtelPayment extends PaymentMethod {

    @Column(nullable = false)
    private String phoneNumber;
}
