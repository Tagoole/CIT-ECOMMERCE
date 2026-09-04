package com.ecommerce.demo.features.payment.repository;

import com.ecommerce.demo.features.payment.model.PaypalPaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaypalPaymentRepository extends JpaRepository<PaypalPaymentModel, Long> {
}
