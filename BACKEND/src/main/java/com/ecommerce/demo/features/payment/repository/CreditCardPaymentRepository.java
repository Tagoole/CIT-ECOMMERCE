package com.ecommerce.demo.features.payment.repository;

import com.ecommerce.demo.features.payment.model.CreditCardPaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardPaymentRepository extends JpaRepository<CreditCardPaymentModel, Long> {
}
