package com.enigmacamp.enigmacoop.repository;

import com.enigmacamp.enigmacoop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,String> {
}
