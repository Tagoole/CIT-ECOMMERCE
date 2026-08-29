package com.ecommerce.demo.features.payment.repository;

import com.ecommerce.demo.features.payment.model.AirtelPaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirtelPaymentRepository extends JpaRepository<AirtelPaymentModel, Long> {

}
