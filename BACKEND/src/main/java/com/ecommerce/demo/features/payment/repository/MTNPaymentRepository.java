package com.ecommerce.demo.features.payment.repository;

import com.ecommerce.demo.features.payment.model.MTNPaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MTNPaymentRepository extends JpaRepository<MTNPaymentModel, Long> {
}
